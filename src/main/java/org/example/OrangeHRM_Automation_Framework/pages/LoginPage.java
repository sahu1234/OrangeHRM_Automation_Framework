package org.example.OrangeHRM_Automation_Framework.pages;

import org.example.OrangeHRM_Automation_Framework.base.DriverFactory;
import org.example.OrangeHRM_Automation_Framework.utils.ConfigReader;
import org.example.OrangeHRM_Automation_Framework.utils.LocatorReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // 1. Private BY LOCATORS (Strict Encapsulatio)
    private final By userNameField = LocatorReader.getBy("login.username.field");
    private final By passwordField = LocatorReader.getBy("login.password.field");
    private final By loginButton = LocatorReader.getBy("login.submit.button");
    private final By invalidCredentialBanner = LocatorReader.getBy("login.error.banner");

    //2. Constructors
    //Standard constructor accepting the active WebDriver instance.

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,Duration.ofSeconds(ConfigReader.getTimeout()));
    }

    //Convenience no-arg constructor that fetches the current thread's driver from DriverFactory.

    public LoginPage() {
        this(DriverFactory.getDriver());
    }

    //Public Action Methods

    //* Waits for the username field to be visible, clears it, and types the username.
    public void enterUsername(String username) {
        WebElement userElement =  wait.until(ExpectedConditions.visibilityOfElementLocated(userNameField));
        userElement.clear();
        userElement.sendKeys(username);
    }

    //  Waits for the password field to be visible, clears it, and types the password.
    public void enterPassword(String password) {
        WebElement passElement = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        passElement.clear();
        passElement.sendKeys(password);
    }

    /**
     * Waits for the Login button to be clickable and clicks it.
     */
    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }


    /**
     * High-level convenience method combining username, password, and login click in one call.
     */
    public void loginToApplication(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    /**
     * Waits for the 'Invalid credentials' error banner to appear and returns its text.
     */

    public String getInvalidCreditailMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(invalidCredentialBanner)).getText();
    }

    /**
     * Checks whether the 'Invalid credentials' error banner is currently displayed on screen.
     */
    public boolean isInvalidCredentialsBannerDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(invalidCredentialBanner)).isDisplayed();
        } catch (Exception e){
            return false;
        }
    }



}
