package com.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.util.List;

public class PaymentPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public PaymentPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    /**
     * Выбирает нужный вариант оплаты через JS + dispatchEvent('change')
     * и ждёт появления маркера формы на странице.
     */
    public void selectPaymentType(String type) {
        String script = """
            let sel = document.getElementById('pay');
            for (let i = 0; i < sel.options.length; i++) {
              if (sel.options[i].text.trim() === arguments[0]) {
                sel.selectedIndex = i;
                break;
              }
            }
            sel.dispatchEvent(new Event('change', { bubbles: true }));
            """;
        ((JavascriptExecutor) driver).executeScript(script, type);

        By marker;
        switch (type) {
            case "Услуги связи"      -> marker = By.id("connection-phone");
            case "Домашний интернет" -> marker = By.id("internet-phone");
            case "Рассрочка"         -> marker = By.id("score-instalment");
            case "Задолженность"     -> marker = By.id("score-arrears");
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(marker));
    }

    public WebElement getPhoneField() {
        return driver.findElement(By.id("connection-phone"));
    }

    public WebElement getSumField() {
        return driver.findElement(By.id("connection-sum"));
    }

    public WebElement getEmailField() {
        return driver.findElement(By.id("connection-email"));
    }

    public WebElement getContinueButton() {
        return driver.findElement(By.cssSelector("#pay-connection button[type='submit']"));
    }

    public String getPlaceholder(WebElement element) {
        return element.getAttribute("placeholder");
    }

    public WebElement getAnyField(String formId, String inputId) {
        return driver.findElement(By.cssSelector("#" + formId + " #" + inputId));
    }

    public WebElement getBlockTitle() {
        return driver.findElement(By.xpath("//h2[contains(.,'Онлайн пополнение')]"));
    }

    public List<WebElement> getPaymentLogos() {
        return driver.findElements(By.cssSelector(".pay__partners ul li img"));
    }

    public WebElement getServiceLink() {
        return driver.findElement(By.cssSelector("a[href*='/help/poryadok-oplaty']"));
    }

    public void waitAndClickWithJS(By selector) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(selector));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    public void clickElementWithJS(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
