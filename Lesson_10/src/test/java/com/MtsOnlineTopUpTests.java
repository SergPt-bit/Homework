package com;

import com.pages.PaymentPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MtsOnlineTopUpTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private PaymentPage page;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("https://www.mts.by/");
        page = new PaymentPage(driver);
        closeCookieBanner();
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    void closeCookieBanner() {
        List<WebElement> banners = driver.findElements(By.cssSelector(".cookie.show"));
        if (!banners.isEmpty()) {
            try {
                WebElement banner = banners.get(0);
                WebElement button = banner.findElement(By.cssSelector("button"));
                button.click();
                wait.until(ExpectedConditions.invisibilityOf(banner));
            } catch (Exception ignored) {}
        }
    }

    @Test
    void checkBlockTitle() {
        String title = page.getBlockTitle().getText().replace("\n", " ");
        Assertions.assertTrue(title.contains("Онлайн пополнение без комиссии"));
    }

    @Test
    void checkPaymentLogosPresence() {
        List<WebElement> logos = page.getPaymentLogos();
        Assertions.assertFalse(logos.isEmpty());

        List<String> expected = List.of("Visa", "Verified By Visa", "MasterCard", "MasterCard Secure Code", "Белкарт");
        for (String alt : expected) {
            boolean found = logos.stream().anyMatch(img -> alt.equalsIgnoreCase(img.getAttribute("alt")));
            Assertions.assertTrue(found, "Логотип не найден: " + alt);
        }
    }

    @Test
    void checkServiceLink() {
        WebElement link = page.getServiceLink();
        Assertions.assertEquals("Подробнее о сервисе", link.getText());
        link.click();
        wait.until(ExpectedConditions.urlContains("/help/poryadok-oplaty-i-bezopasnost-internet-platezhey"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/help/poryadok-oplaty-i-bezopasnost-internet-platezhey"));
    }

    @Test
    void checkFormFillAndRedirect() {
        page.selectPaymentType("Услуги связи");
        page.getPhoneField().sendKeys("297777777");
        page.getSumField().sendKeys("10");
        page.getEmailField().sendKeys("test@example.com");

        Assertions.assertTrue(page.getContinueButton().isEnabled());
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", page.getContinueButton());

        wait.until(ExpectedConditions.urlContains("widget"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("widget"));
    }

    @Test
    void checkPlaceholdersForAllForms() {
        page.selectPaymentType("Услуги связи");
        Assertions.assertEquals("Номер телефона", page.getPlaceholder(page.getPhoneField()));
        Assertions.assertEquals("Сумма", page.getPlaceholder(page.getSumField()));
        Assertions.assertEquals("E-mail для отправки чека", page.getPlaceholder(page.getEmailField()));

        page.selectPaymentType("Домашний интернет");
        Assertions.assertEquals("Номер абонента", page.getPlaceholder(page.getAnyField("pay-internet", "internet-phone")));
        Assertions.assertEquals("Сумма", page.getPlaceholder(page.getAnyField("pay-internet", "internet-sum")));
        Assertions.assertEquals("E-mail для отправки чека", page.getPlaceholder(page.getAnyField("pay-internet", "internet-email")));

        page.selectPaymentType("Рассрочка");
        Assertions.assertEquals("Номер счета на 44", page.getPlaceholder(page.getAnyField("pay-instalment", "score-instalment")));
        Assertions.assertEquals("Сумма", page.getPlaceholder(page.getAnyField("pay-instalment", "instalment-sum")));
        Assertions.assertEquals("E-mail для отправки чека", page.getPlaceholder(page.getAnyField("pay-instalment", "instalment-email")));

        page.selectPaymentType("Задолженность");
        Assertions.assertEquals("Номер счета на 2073", page.getPlaceholder(page.getAnyField("pay-arrears", "score-arrears")));
        Assertions.assertEquals("Сумма", page.getPlaceholder(page.getAnyField("pay-arrears", "arrears-sum")));
        Assertions.assertEquals("E-mail для отправки чека", page.getPlaceholder(page.getAnyField("pay-arrears", "arrears-email")));
    }
}
