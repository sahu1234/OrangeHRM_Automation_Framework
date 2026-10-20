package testcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.OrangeHRM_Automation_Framework.base.BaseTest;
import org.example.OrangeHRM_Automation_Framework.base.DriverFactory;
import org.example.OrangeHRM_Automation_Framework.utils.ConfigReader;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.example.OrangeHRM_Automation_Framework.pages.DashboardPage;
import org.example.OrangeHRM_Automation_Framework.pages.LoginPage;

import java.time.Duration;

public class DashBoardPageTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(DashBoardPageTest.class);

    @Test(priority = 1, description = "Verify Dashboard header, sidebar navigation to PIM and Admin, and Logout flow")
    public void testDashboardNavigationAndLogout() {
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        DashboardPage dashboardPage = new DashboardPage(DriverFactory.getDriver());

        WebDriverWait wait = new WebDriverWait(
                DriverFactory.getDriver(),
                Duration.ofSeconds(ConfigReader.getTimeout())
        );

        // 1, Log in to OrangeHRM application
        logger.info("Step 1 : Logging in to OrangeHRM");
        loginPage.enterUsername(ConfigReader.getProperty("username"));
        loginPage.enterPassword(ConfigReader.getProperty("password"));
        loginPage.clickLogin();

        //2. Verify we have landed on the Dashnboard Page
        logger.info("Step 2: verifying Dashboard header title");
        Assert.assertEquals(
                dashboardPage.getPageHeaderTitle(),
                "Dashboard",
                "Dashboard header title did not macth!"
        );

        //3. Click 'PIM' in the left sidebar and verify the page header changes to 'PIM'

        logger.info("Step3: Navigating to PIM module from sidebar");
        dashboardPage.navigateToPIM();
        wait.until(ExpectedConditions.urlContains("pim"));
        Assert.assertEquals(
                dashboardPage.getPageHeaderTitle(),
                "PIM",
                "Failed to navigate to the PIM module! "
        );

        // 4. Click 'Admin' in the left sidebar and verify the page header changes to 'Admin'
        logger.info("Step 4: Navigating to Admin module from sidebar");
        dashboardPage.navigateToAdmin();
        wait.until(ExpectedConditions.urlContains("admin"));
        Assert.assertEquals(
                dashboardPage.getPageHeaderTitle(),
                "Admin",
                "Failed to navigate to the Admin module!"
        );

        // 5. Open the top-right user dropdown, click Logout, and verify return to the Login page
        logger.info("Step 5: Logging out of the applicatipn");
        dashboardPage.logoutFromApplication();
        boolean isBackonLoginPage =  wait.until(ExpectedConditions.urlContains("auth/login"));
        Assert.assertTrue(
                isBackonLoginPage,
                "User was not redirected to Login Page after clicking Logout !"
        );

    }




}
