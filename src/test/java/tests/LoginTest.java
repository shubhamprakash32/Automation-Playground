package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.Login;
import utils.CSVReader;
import utils.ScreenshotUtil;
import utils.WaitUtil;

import java.util.List;

public class LoginTest extends BaseTest {

    private Login loginPage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new Login(driver);
    }

    @Test(dataProvider = "validUser")
    public void testLogin(String username, String password) throws InterruptedException {
        loginPage.login(username, password);

        // Validate login by dashboard title
        boolean isLoggedIn = loginPage.isLoginSuccessful();
        try {
            Assert.assertTrue(isLoggedIn, "Login failed for user: " + username);
        } catch (AssertionError e) {
            ScreenshotUtil.capture(driver, username + "_loginFail");
            throw e;
        }

        // Logout verification
        loginPage.logout();
        WaitUtil.waitForElementToLoad();
        boolean atLoginPage = loginPage.isLogoutSuccessful(); // verify redirected
        try {
            Assert.assertTrue(atLoginPage, "Logout failed; user not redirected to login page: " + username);

        } catch (AssertionError e) {
            ScreenshotUtil.capture(driver, username + "_logoutFail");
            throw e;
        }
    }

    @Test(dataProvider = "invalidUsers")
    public void testInvalidLogin(String username, String password) {
        loginPage.login(username, password);

        // Capture and assert error message
        String actualError = loginPage.getErrorMessage();
        try {
            Assert.assertEquals(actualError, "Invalid username or password",
                    "Error message mismatch for user: " + username);
        } catch (AssertionError e) {
            ScreenshotUtil.capture(driver, username + "_invalidLoginFail");
            throw e;
        }
    }

    @DataProvider(name = "validUser")
    public Object[][] validUser() {
        List<String[]> data = CSVReader.readCSV("testdata/users.csv");
        Object[][] arr = new Object[data.size()][2];
        for (int i = 0; i < data.size(); i++) {
            arr[i][0] = data.get(i)[0];
            arr[i][1] = data.get(i)[1];
        }
        return arr;
    }

    @DataProvider(name = "invalidUsers")
    public Object[][] invalidUsers() {
        List<String[]> data = CSVReader.readCSV("testdata/invalidUsers.csv");
        Object[][] arr = new Object[data.size()][2];
        for (int i = 0; i < data.size(); i++) {
            arr[i][0] = data.get(i)[0];
            arr[i][1] = data.get(i)[1];
        }
        return arr;
    }

}
