package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.OrangeHRM_Automation_Framework.base.DriverFactory;
import org.example.OrangeHRM_Automation_Framework.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;



public class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    /**
     * Runs automatically BEFORE every @Test method.
     * Launches the configured browser, maximizes the window, and opens OrangeHRM.
     */
    @BeforeMethod(alwaysRun = true)
    public void setup() {

        logger.info("===== Starting Browser Session =====");
        // 1. Initialize the thread-safe WebDriver via DriverFactory (reads browser & timeout from ConfigReader)
        WebDriver driver = DriverFactory.initDriver();

        // 2. Ensure the browser window is maximized
        driver.manage().window().maximize();

        //3. Navigate to the OrangeHRM URL from config . Properties
        String appUrl = ConfigReader.getUrl();
        logger.info("Navigating to URL: {}", appUrl);
        driver.get(appUrl);
    }

    /**
     *  Runs automatically AFTER every @Test method (even if the test fails).
     *      * Calls quit() on the browser and clears the ThreadLocal reference.
     *      */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        logger.info("Closing browser session and cleaning up ThreadLocal driver.");
        DriverFactory.quitDriver();
        logger.info("===== Browser Session Closed =====");
    }
}
