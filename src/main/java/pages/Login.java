package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WaitUtil;

import java.sql.SQLOutput;

public class Login {
    private final WebDriver driver;

    // Locators
    @FindBy (id = "username")
    private WebElement usernameField;

    @FindBy (id = "password")
    private WebElement passwordField;

    @FindBy (xpath = "//button[text()='Login']")
    private WebElement loginButton;

    @FindBy (xpath = "//button[text()='Logout']")
    private WebElement logoutButton;

    @FindBy (tagName = "h1")
    private WebElement dashboardTitle;

    @FindBy (tagName = "h2")
    private WebElement loginTittle;

    @FindBy (xpath = "//div[text()='Invalid username or password']")
    private WebElement errorMessage;

    // Constructor
    public Login(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Actions
    public void enterUsername(String username) {
        WaitUtil.waitForVisibility(driver, usernameField, 10);
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        WaitUtil.waitForVisibility(driver, passwordField, 10);
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        WaitUtil.waitForClickable(driver, loginButton, 10);
        loginButton.click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // Invalid login message
    public String getErrorMessage() {
        try {
            WaitUtil.waitForVisibility(driver, errorMessage, 10); // wait until visible
            return errorMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }

    // Method to get dashboard title
    public String getDashboardTitle() {
        try {
            WaitUtil.waitForVisibility(driver, dashboardTitle, 10);
            return dashboardTitle.getText();
        } catch (Exception e) {
            return "";
        }
    }

    // Method to validate login success
    public boolean isLoginSuccessful() {
        return getDashboardTitle().equals("Gaming Sales Dashboard");
    }

    public void logout() {
        WaitUtil.waitForClickable(driver, logoutButton, 10); // wait until clickable
        logoutButton.click();
    }

    // Method to get login page title
    public String getLoginTitle() {
        try {
            WaitUtil.waitForVisibility(driver, loginTittle, 10);
            return loginTittle.getText();
        } catch (Exception e) {
            return "";
        }
    }

    // Method to validate logout success
    public boolean isLogoutSuccessful() {
        return getLoginTitle().equals("Gaming Dashboard Login");
    }
}
