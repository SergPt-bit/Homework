package com;

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

    @BeforeAll
    void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.mts.by/");
        closeCookieBannerIfPresent(); 
    }

    @AfterAll
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void closeCookieBannerIfPresent() {
        try {
            WebElement cookieBanner = driver.findElement(By.cssSelector("div.cookie.show"));
            WebElement closeButton = cookieBanner.findElement(By.cssSelector("button"));
            if (closeButton.isDisplayed()) {
                closeButton.click();
                wait.until(ExpectedConditions.invisibilityOf(cookieBanner));
            }
        } catch (NoSuchElementException | TimeoutException ignored) {
            
        }
    }

    private void safeClick(WebElement element) {
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    @Test
    @DisplayName("Проверка заголовка блока 'Онлайн пополнение без комиссии'")
    void checkBlockTitle() {
        WebElement title = driver.findElement(By.xpath("//h2[contains(text(),'Онлайн пополнение')]"));
        Assertions.assertTrue(title.getText().replace("\n", " ").contains("Онлайн пополнение без комиссии"));
    }

    @Test
    @DisplayName("Проверка логотипов платёжных систем")
    void checkPaymentLogos() {
        List<WebElement> logos = driver.findElements(By.cssSelector(".pay__partners ul li img"));
        Assertions.assertFalse(logos.isEmpty());

        boolean visa = logos.stream().anyMatch(e -> e.getAttribute("alt").toLowerCase().contains("visa"));
        boolean mc = logos.stream().anyMatch(e -> e.getAttribute("alt").toLowerCase().contains("mastercard"));
        boolean belkart = logos.stream().anyMatch(e -> e.getAttribute("alt").toLowerCase().contains("белкарт"));

        Assertions.assertTrue(visa);
        Assertions.assertTrue(mc);
        Assertions.assertTrue(belkart);
    }

    @Test
    @DisplayName("Проверка ссылки 'Подробнее о сервисе'")
    void checkServiceLink() {
        WebElement link = driver.findElement(By.cssSelector("a[href='/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/']"));
        Assertions.assertEquals("Подробнее о сервисе", link.getText());
        safeClick(link);

        wait.until(ExpectedConditions.urlContains("/help/poryadok-oplaty-i-bezopasnost-internet-platezhey"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/help/poryadok-oplaty-i-bezopasnost-internet-platezhey"));
    }

    @Test
    @DisplayName("Проверка формы 'Услуги связи'")
    void checkFormFillAndSubmit() {
        new Select(driver.findElement(By.id("pay"))).selectByVisibleText("Услуги связи");

        driver.findElement(By.id("connection-phone")).sendKeys("297777777");
        driver.findElement(By.id("connection-sum")).sendKeys("10");
        driver.findElement(By.id("connection-email")).sendKeys("test@example.com");

        WebElement button = driver.findElement(By.cssSelector("#pay-connection button[type='submit']"));
        Assertions.assertTrue(button.isEnabled());
        safeClick(button);

        wait.until(driver -> driver.getCurrentUrl().contains("mts.by"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("mts.by"));
    }
}
