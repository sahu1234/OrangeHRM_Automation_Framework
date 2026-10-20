package org.example.OrangeHRM_Automation_Framework.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();
    private static final String CONFIG_FILE_PATH = "src/test/resources/config/config.properties";

    // Static block runs once automatically when the class is first loaded

    static {
        try(FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fis);
        } catch (IOException e){
            throw new RuntimeException("Failed to load config.properties from path" + CONFIG_FILE_PATH, e);
        }
    }

    /**
     * Fetches any property value by its key name.
     */
    public static String getProperty(String Key) {
        String value = properties.getProperty(Key);
        if(value == null || value.trim().isEmpty()) {
            throw new RuntimeException("Property '" + Key + "' is not specified in config.Properties.");
        } return value.trim();
    }

    /**
     * Convenience method to get the configured browser name (e.g., "chrome").
     */
    public static String getBrower(){
        return getProperty("browser");
    }

    /**
     * Convenience method to get the application URL.
     */
    public static String getUrl() {
        return getProperty("url");
    }

    /**
     * Convenience method to get the default timeout as a long for Selenium 4 Duration.ofSeconds().
     */
    public static long getTimeout() {
        return Long.parseLong(getProperty("timeout"));
    }




}
