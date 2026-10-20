package org.example.OrangeHRM_Automation_Framework.utils;

import org.openqa.selenium.By;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class LocatorReader {

    private static final Properties properties = new Properties();

    static {
        // Automatically checks root resources first, then config/ subfolder

        String filePath = "src/test/resources/Locators/locators.properties";
        if(!Files.exists(Paths.get(filePath))) {
            filePath = "src/test/resources/Locators/locators.properties";
        }

        try(FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load locators.properites from path: " + filePath, e);
        }
    }
    /**
     * Reads a key from locators.properties (e.g., "name:username") and returns the Selenium By locator.
     */
    public static By getBy(String Key) {
        String rawValue = properties.getProperty(Key);
        if(rawValue == null || !rawValue.contains(":")){
            throw new IllegalArgumentException(
                    "Invalid or missing locator Key '" + Key + "'. Expected format 'type:value' in locators.properties."
            );
        }
        // Split only on the first colon so XPaths or CSS selectors containing colons remain intact
        String [] parts = rawValue.trim().split(":",2);
        String locatorType = parts[0].trim().toLowerCase();
        String locatorValue = parts[1].trim();

        switch (locatorType) {
            case "id":
                return By.id(locatorValue);
            case "name":
                return By.name(locatorValue);
            case "css":
            case "cssselector":
                return By.cssSelector(locatorValue);
            case "xpath":
                return By.xpath(locatorValue);
            case "classname":
                return By.className(locatorValue);
            case "linktext":
                return By.linkText(locatorValue);
            case "partiallinktext":
                return By.partialLinkText(locatorValue);
            case "tagname":
                return By.tagName(locatorValue);
            default:
                throw new IllegalArgumentException("Unsupported locator type '" + locatorType + "' for key: " + Key);

        }
    }


}
