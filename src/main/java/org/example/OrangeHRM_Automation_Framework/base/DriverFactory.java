package org.example.OrangeHRM_Automation_Framework.base;

import org.example.OrangeHRM_Automation_Framework.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.Locale;

public class DriverFactory {

    // ThreadLocal container that gives each test thread its own isolated WebDriver instance
    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    /**
     * Reads the browser from config.properties, launches the browser, and stores it in ThreadLocal.
     */

    public static WebDriver initDriver() {
        String browser = ConfigReader.getBrower().toLowerCase();
        boolean isHeadless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));

        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) {
                    chromeOptions.addArguments("--headless= new");
                }
                tlDriver.set(new ChromeDriver(chromeOptions));
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless) {
                    firefoxOptions.addArguments("--headless");
                }
                tlDriver.set(new FirefoxDriver(firefoxOptions));
                break;

            case "edge":
                tlDriver.set(new EdgeDriver());
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported browser specified in config.properties: " + browser
                );
        }

        //Configure browser window and timeouts for the current thread's driver

        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getTimeout()));

        return getDriver();
    }

    /**
     * Returns the WebDriver instance belonging to the currently running thread.
     */
    public static WebDriver getDriver() {
        return tlDriver.get();
    }
    /**
     * Quits the browser and clears the ThreadLocal reference to prevent memory leaks.
     */
    public static void quitDriver() {
        if (tlDriver.get() != null) {
            tlDriver.get().quit();
            tlDriver.remove(); // Critical step: cleans up the thread's locker in memory
        }
    }
}
