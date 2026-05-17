package tests;

import config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import pages.LoginPage;
import pages.MainPage;

public class LoginTest extends BaseTest {

    @Test
    public void testSuccessfulLogin() {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestConfig.getUserEmail(), TestConfig.getUserPassword());

        Assert.assertTrue("User should be logged in after valid credentials",
                loginPage.isLoggedIn());
    }

    @Test
    public void testFailedLoginWithWrongPassword() {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestConfig.getUserEmail(), "wrongpassword");

        String error = loginPage.getErrorMessage();
        Assert.assertTrue("Error message should indicate incorrect credentials",
                error.contains("incorrect"));
    }

    @Test
    public void testFailedLoginWithEmptyFields() {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "");

        String error = loginPage.getErrorMessage();
        Assert.assertTrue("Error message should ask to fill in fields",
                error.toLowerCase().contains("fill"));
    }

    @Test
    public void testSignUpWithRandomCredentials() {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.signUpWithRandomCredentials();

        Assert.assertTrue("User should be logged in after signup",
                loginPage.isLoggedIn());
    }

    @Test
    public void testLogout() {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestConfig.getUserEmail(), TestConfig.getUserPassword());
        Assert.assertTrue(loginPage.isLoggedIn());

        loginPage.logout();
        Assert.assertFalse("User should be logged out", loginPage.isLoggedIn());
    }
}
