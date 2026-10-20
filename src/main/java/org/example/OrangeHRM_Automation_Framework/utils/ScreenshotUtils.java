package org.example.OrangeHRM_Automation_Framework.utils;

import org.example.OrangeHRM_Automation_Framework.base.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    /**
     * Saves a physical .png screenshot in reports/screenshots/ and returns its file path.
     */

    public static String captureScreensgotFile(String testName) {
        WebDriver driver = DriverFactory.getDriver();
        if(driver == null) {
            return null;
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotDir = System.getProperty("user.dir") + "/reports/screenshots";
        String filePath = screenshotDir + testName + "_" +timestamp + ".png";

        try {
            Path dirPath = Paths.get(screenshotDir);
            if(!Files.exists(dirPath)){
                Files.createDirectories(dirPath);
            }
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(srcFile.toPath(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            return filePath;
        } catch (IOException e) {
            System.err.println("Failed to save screenshot fie:"+ e.getMessage());
            return null;
        }
    }

    /**
     * Captures a Base64 screenshot string to embed directly inside ExtentReport.html.
     */
    public static String captureBase64Screenshot() {
        WebDriver driver =  DriverFactory.getDriver();
        if(driver == null) {
            return null;
        }
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }

}
