package com.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class PaymentPage {

    private final WebDriver driver;

    private final By paymentTypeSelect = By.id("pay");
    private final By phoneField = By.id("connection-phone");
    private final By sumField = By.id("connection-sum");
    private final By emailField = By.id("connection-email");
    private final By continueButton = By.cssSelector("#pay-connection button[type='submit']");
    private final By paymentLogos = By.cssSelector(".pay__partners ul li img");
    private final By serviceLink = By.cssSelector("a[href='/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/']");
    private final By blockTitle = By.xpath("//h2[contains(text(),'Онлайн пополнение')]");

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectPaymentType(String type) {
        new Select(driver.findElement(paymentTypeSelect)).selectByVisibleText(type);
    }

    public WebElement getPhoneField() {
        return driver.findElement(phoneField);
    }

    public WebElement getSumField() {
        return driver.findElement(sumField);
    }

    public WebElement getEmailField() {
        return driver.findElement(emailField);
    }

    public WebElement getContinueButton() {
        return driver.findElement(continueButton);
    }

    public String getPlaceholder(WebElement element) {
        return element.getAttribute("placeholder");
    }

    public WebElement getAnyField(String formId, String inputId) {
        return driver.findElement(By.cssSelector("#" + formId + " #" + inputId));
    }

    public List<WebElement> getPaymentLogos() {
        return driver.findElements(paymentLogos);
    }

    public WebElement getServiceLink() {
        return driver.findElement(serviceLink);
    }

    public WebElement getBlockTitle() {
        return driver.findElement(blockTitle);
    }
}
