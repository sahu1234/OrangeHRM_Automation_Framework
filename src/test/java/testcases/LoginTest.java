package testcases;

import org.example.OrangeHRM_Automation_Framework.base.BaseTest;
import org.example.OrangeHRM_Automation_Framework.base.DriverFactory;
import org.example.OrangeHRM_Automation_Framework.utils.ConfigReader;
import org.example.OrangeHRM_Automation_Framework.utils.ExcelUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.example.OrangeHRM_Automation_Framework.pages.LoginPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class LoginTest extends BaseTest {

    private static final String EXCEL_PATH = "src/test/resources/testdata/OrangeHRM_TestData.xlsx";
    /**
     * Supplies rows from the 'LoginData' sheet in OrangeHRM_TestData.xlsx to the @Test method.
     */
    @DataProvider(name = "excelLoginData")
    public Object[][] getLoginData() {
        return ExcelUtils.getExcelData(EXCEL_PATH, "LoginData");
    }

    /**
     * Executes automatically once for every row in the Excel sheet (3 rows = 3 test runs).
     */
    private static final Logger logger = LogManager.getLogger(LoginTest.class);
    @Test(dataProvider = "excelLoginData", description = "Data-Driven Login Test using Apache POI Excel sheet")
    public void testDataDrivenLogin(String username, String password, String expectedResult) {

        logger.info("Starting login test with Username: '{}' | Expected Result: '{}'", username, expectedResult);
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        if (expectedResult.equalsIgnoreCase("VALID")) {
            WebDriverWait wait = new WebDriverWait(
                    DriverFactory.getDriver(),
                    Duration.ofSeconds(ConfigReader.getTimeout())
            );
            boolean isRedirected = wait.until(ExpectedConditions.urlContains("dashboard"));
            logger.info("Valid login successful for user '{}'. Redirected to Dashboard.", username);
            Assert.assertTrue(
                    isRedirected,
                    "Expected valid login for user '" + username + "' to land on Dashboard."
            );
        } else {
            Assert.assertTrue(
                    loginPage.isInvalidCredentialsBannerDisplayed(),
                    "Expected error banner for invalid login with user '" + username + "'."
            );
            String errorMsg = loginPage.getInvalidCreditailMessage();
            logger.warn("Invalid login attempt for user '{}'. Captured banner: '{}'", username, errorMsg);
            Assert.assertEquals(
                    loginPage.getInvalidCreditailMessage(),
                    "Invalid credentials",
                    "Error message text mismatch!"
            );
        }
    }

    /*@Test(priority = 2, description = "Verify that a valid user can log in and land on the Dashboard page")
    public void testValidLogin(){
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());

        // 1. Perform login using Admin / admin123 from config.properties
        loginPage.enterUsername(ConfigReader.getProperty("username"));
        loginPage.enterPassword(ConfigReader.getProperty("password"));
        loginPage.clickLogin();

        // 2. Wait for OrangeHRM to redirect from /auth/login to /dashboard/index
        WebDriverWait wait = new WebDriverWait(
                DriverFactory.getDriver(),
                Duration.ofSeconds(ConfigReader.getTimeout())
        );
        boolean isRedirectedToDashboard = wait.until(ExpectedConditions.urlContains("dashboard"));

        // 3. Assert that the browser landed on the Dashboard URL

        String currentUrl = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(
                isRedirectedToDashboard && currentUrl.contains("/dashboard/index"),
                        "Expected to land on the Dashboard page, but current URL was: " + currentUrl);
    }

    @Test(priority = 3, description = "Verify that an invalid login displays the 'Invalid credentials' error banner" )
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());

                //1. Enter Valid usernae with an incorrect Password
        loginPage.enterUsername("Admin");
        loginPage.enterPassword("wrongPassword123");
        loginPage.clickLogin();

        // 2. Assert that the error banner is displayed and mactehs the expected test
        Assert.assertTrue(
                loginPage.isInvalidCredentialsBannerDisplayed(),
                "Expected the Invalid credentials error banner to be displayed, but it was not visible."
        );

        Assert.assertEquals(
                loginPage.getInvalidCreditailMessage(),
                "Invalid credentials",
                "Error banner text did not match expected message!"
        );

    }*/


}
