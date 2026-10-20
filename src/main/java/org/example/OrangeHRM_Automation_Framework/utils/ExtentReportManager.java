package org.example.OrangeHRM_Automation_Framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> tlExtentTest = new ThreadLocal<>();
    /**
     * Initializes the ExtentSparkReporter HTML dashboard at reports/ExtentReport.html.
     */

    public static synchronized ExtentReports getInstance() {
        if(extent == null) {
            String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("OrangeHRM Automation Report");
            sparkReporter.config().setReportName("OrangeHRM Test Execution Dashboard");
            sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            //Add enviroment details to the Dashboard
            extent.setSystemInfo("Application URL", ConfigReader.getUrl());
            extent.setSystemInfo("Browser", ConfigReader.getBrower());
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
        return extent;
    }

    public static ExtentTest createTest(String testName, String description) {
        ExtentTest test = getInstance().createTest(testName, description);
        tlExtentTest.set(test);
        return test;
    }

    public static ExtentTest getTest() {
        return tlExtentTest.get();
    }

    public static void flushReports(){
        if(extent != null) {
            extent.flush();
        }
    }

}
