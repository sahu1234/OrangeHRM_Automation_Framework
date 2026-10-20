package org.example.OrangeHRM_Automation_Framework.pages;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.OrangeHRM_Automation_Framework.base.DriverFactory;
import org.example.OrangeHRM_Automation_Framework.utils.ConfigReader;
import org.example.OrangeHRM_Automation_Framework.utils.LocatorReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {

    private static final Logger logger = LogManager.getLogger(DashboardPage.class);
    private final WebDriver driver;
    private final WebDriverWait wait;

    // =========================================================================
    // 1. PRIVATE BY LOCATORS (Loaded Dynamically from locators.properties)
    // =========================================================================
    private final By headerTitle = LocatorReader.getBy("dashboard.header.title");
    private final By userDropdownTab = LocatorReader.getBy("dashboard.user.dropdown");
    private final By logoutLink = LocatorReader.getBy("dashboard.logout.link");
    private final By adminMenuLink = LocatorReader.getBy("dashboard.menu.admin");
    private final By pimMenuLink = LocatorReader.getBy("dashboard.menu.pim");
    private final By leaveMenuLink = LocatorReader.getBy("dashboard.menu.leave");

    // =========================================================================
    // 2. CONSTRUCTORS
    // =========================================================================

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait =  new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getTimeout()));
    }

    public DashboardPage() {
        this(DriverFactory.getDriver());
    }

    // =========================================================================
    // 3. PUBLIC ACTION METHODS
    // =========================================================================

    public String getPageHeaderTitle() {
        String title = wait.until(ExpectedConditions.visibilityOfElementLocated(headerTitle)).getText();
        logger.info("Captured Page header title: '{}'", title);
        return title;
    }

    public  void navigateToPIM() {
        logger.info("Clicking on PIM menu in the left sidebar");
        wait.until(ExpectedConditions.elementToBeClickable(pimMenuLink)).click();
    }

    public void navigateToAdmin() {
        logger.info("Clicking on Admin menu in the left sidebar.");
        wait.until(ExpectedConditions.elementToBeClickable(adminMenuLink)).click();
    }
    public void navigateToLeave() {
        logger.info("Clicking on Leave menu in the left sidebar.");
        wait.until(ExpectedConditions.elementToBeClickable(leaveMenuLink)).click();
    }
    public void logoutFromApplication() {
        logger.info("Opening user profile dropdown to log out.");
        wait.until(ExpectedConditions.elementToBeClickable(userDropdownTab)).click();

        logger.info("Waiting for Logout option to appear and clicking it.");
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();

    }



}
