package org.example.OrangeHRM_Automation_Framework.base;

import org.example.OrangeHRM_Automation_Framework.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    /**
     * Runs automatically BEFORE every @Test method.
     * Launches the configured browser, maximizes the window, and opens OrangeHRM.
     */
    @BeforeMethod(alwaysRun = true)
    public void setup() {

        // 1. Initialize the thread-safe WebDriver via DriverFactory (reads browser & timeout from ConfigReader)
        WebDriver driver = DriverFactory.initDriver();

        // 2. Ensure the browser window is maximized
        driver.manage().window().maximize();

        //3. Navigate to the OrangeHRM URL from config . Properties
        driver.get(ConfigReader.getUrl());
    }

    /**
     *  Runs automatically AFTER every @Test method (even if the test fails).
     *      * Calls quit() on the browser and clears the ThreadLocal reference.
     *      */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
