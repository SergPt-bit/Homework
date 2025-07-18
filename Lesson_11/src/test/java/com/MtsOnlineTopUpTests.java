package com;

import com.pages.PaymentPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Epic("Онлайн пополнение")
@Feature("Проверка блока оплаты")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MtsOnlineTopUpTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private PaymentPage page;

    @BeforeAll
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("https://www.mts.by/");
        page = new PaymentPage(driver, wait);
        closeCookieBanner();
    }

    @AfterAll
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void closeCookieBanner() {
        driver.findElements(By.cssSelector(".cookie.show")).stream().findFirst().ifPresent(b -> {
            try {
                b.findElement(By.cssSelector("button")).click();
                wait.until(ExpectedConditions.invisibilityOf(b));
            } catch (Exception ignored) {}
        });
    }

    @Test
    @Story("Проверка заголовка блока")
    void checkBlockTitle() {
        String title = page.getBlockTitle()
                           .getText()
                           .replace("\n", " ")
                           .trim();
        Assertions.assertTrue(
            title.contains("Онлайн пополнение без комиссии"),
            "Неверный заголовок: " + title
        );
    }

    @Test
    @Story("Проверка логотипов платёжных систем")
    void checkPaymentLogosPresence() {
        List<String> expected = List.of(
            "Visa", "Verified By Visa",
            "MasterCard", "MasterCard Secure Code",
            "Белкарт"
        );
        List<WebElement> logos = page.getPaymentLogos();
        Assertions.assertFalse(logos.isEmpty(), "Логотипы не найдены");
        expected.forEach(alt -> {
            boolean found = logos.stream()
                                 .anyMatch(img -> alt.equalsIgnoreCase(img.getAttribute("alt")));
            Assertions.assertTrue(found, "Логотип не найден: " + alt);
        });
    }

    @Test
    @Story("Переход по ссылке 'Подробнее о сервисе'")
    void checkServiceLink() {
        By selector = By.cssSelector("a[href*='/help/poryadok-oplaty']");
        page.waitAndClickWithJS(selector);
        wait.until(ExpectedConditions.urlContains("/help/poryadok-oplaty"));
        Assertions.assertTrue(
            driver.getCurrentUrl().contains("/help/poryadok-oplaty"),
            "URL не соответствует ожидаемому"
        );
    }

    @Test
    @Story("Заполнение формы и проверка виджета оплаты")
    void checkFormFillAndWidgetData() {
        // 1) Сохраняем текущее окно и список окон
        String mainWindow = driver.getWindowHandle();
        Set<String> before = driver.getWindowHandles();

        // 2) Заполняем форму
        page.selectPaymentType("Услуги связи");
        page.getPhoneField().sendKeys("297777777");
        page.getSumField().sendKeys("10");
        page.getEmailField().sendKeys("test@example.com");
        Assertions.assertTrue(
            page.getContinueButton().isEnabled(),
            "Кнопка «Продолжить» должна быть активна"
        );
        page.clickElementWithJS(page.getContinueButton());

        // 3) Ждём одного из трёх сценариев:
        //    A) Нового окна
        //    B) Любого <iframe>
        //    C) Появления инлайнового контейнера платежного виджета
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        shortWait.until(d -> {
            boolean newWindow = d.getWindowHandles().size() > before.size();
            boolean hasIframe = !d.findElements(By.tagName("iframe")).isEmpty();
            boolean hasInline = !d.findElements(By.id("bxdynamic_pay-form_end")).isEmpty();
            return newWindow || hasIframe || hasInline;
        });

        // 4) Определяем контекст
        boolean switched = false;

        // A) Новый Window
        Set<String> after = driver.getWindowHandles();
        after.removeAll(before);
        if (!after.isEmpty()) {
            String widgetWindow = after.iterator().next();
            driver.switchTo().window(widgetWindow);
            switched = true;
        } 
        // B) <iframe>
        else {
            List<WebElement> frames = driver.findElements(By.tagName("iframe"));
            if (!frames.isEmpty()) {
                driver.switchTo().frame(frames.get(0));
                switched = true;
            }
        }
        // C) контейнер inline — в этом случае остаёмся в основном document

        // 5) Выполняем проверки внутри выбранного контекста
        String html = driver.getPageSource();

        // Номер телефона
        Assertions.assertTrue(
            html.contains("297777777"),
            "Телефон не отобразился"
        );

        // Сумма и кнопка
        Assertions.assertTrue(
            html.contains("10 BYN"),
            "Сумма не отобразилась"
        );

        // Плейсхолдеры полей карты (если присутствуют)
        List<WebElement> cardNumber = driver.findElements(By.id("card-number"));
        if (!cardNumber.isEmpty()) {
            Assertions.assertEquals(
                "Номер карты",
                cardNumber.get(0).getAttribute("placeholder")
            );
        }

        // Логотипы платежных систем
        List<WebElement> icons = driver.findElements(By.cssSelector(".widget-payment-icons img"));
        for (String name : List.of("Visa", "MasterCard", "Белкарт")) {
            boolean ok = icons.stream()
                              .anyMatch(i -> name.equalsIgnoreCase(i.getAttribute("alt")));
            Assertions.assertTrue(ok, "Иконка не найдена: " + name);
        }

        // 6) Возвращаем контекст
        if (switched) {
            // если это было новое окно — закрываем и возвращаемся в main
            driver.close();
            driver.switchTo().window(mainWindow);
        } else {
            // фрейм или inline — возвращаемся к основному документу
            driver.switchTo().defaultContent();
        }
    }

    @Test
    @Story("Проверка плейсхолдеров всех форм")
    void checkPlaceholdersForAllForms() {
        page.selectPaymentType("Услуги связи");
        Assertions.assertEquals("Номер телефона", page.getPlaceholder(page.getPhoneField()));
        Assertions.assertEquals("Сумма", page.getPlaceholder(page.getSumField()));
        Assertions.assertEquals("E-mail для отправки чека", page.getPlaceholder(page.getEmailField()));

        page.selectPaymentType("Домашний интернет");
        Assertions.assertEquals(
            "Номер абонента",
            page.getPlaceholder(page.getAnyField("pay-internet", "internet-phone"))
        );
        Assertions.assertEquals(
            "Сумма",
            page.getPlaceholder(page.getAnyField("pay-internet", "internet-sum"))
        );
        Assertions.assertEquals(
            "E-mail для отправки чека",
            page.getPlaceholder(page.getAnyField("pay-internet", "internet-email"))
        );

        page.selectPaymentType("Рассрочка");
        Assertions.assertEquals(
            "Номер счета на 44",
            page.getPlaceholder(page.getAnyField("pay-instalment", "score-instalment"))
        );
        Assertions.assertEquals(
            "Сумма",
            page.getPlaceholder(page.getAnyField("pay-instalment", "instalment-sum"))
        );
        Assertions.assertEquals(
            "E-mail для отправки чека",
            page.getPlaceholder(page.getAnyField("pay-instalment", "instalment-email"))
        );

        page.selectPaymentType("Задолженность");
        Assertions.assertEquals(
            "Номер счета на 2073",
            page.getPlaceholder(page.getAnyField("pay-arrears", "score-arrears"))
        );
        Assertions.assertEquals(
            "Сумма",
            page.getPlaceholder(page.getAnyField("pay-arrears", "arrears-sum"))
        );
        Assertions.assertEquals(
            "E-mail для отправки чека",
            page.getPlaceholder(page.getAnyField("pay-arrears", "arrears-email"))
        );
    }
}
