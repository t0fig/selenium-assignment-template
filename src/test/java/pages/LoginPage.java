package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.UUID;

public class LoginPage extends BasePage {
    private final By emailInput = By.id("n-email");
    private final By passwordInput = By.id("n-password2");
    private final By signInButton = By.id("goto-signin-btn");
    private final By signUpTopButton = By.id("signup");
    private final By signUpEmail = By.id("s-email");
    private final By signUpPassword = By.id("s-password2");
    private final By registerButton = By.id("register-btn");
    private final By logoutButton = By.id("logout");
    private final By errorMessage = By.className("error-message");
    private final By loginMessage = By.id("login-message");
    private final By modalOverlay = By.cssSelector(".vm--modal");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String email, String password) {
        waitForElement(modalOverlay);
        waitForElement(emailInput).sendKeys(email);
        waitForElement(passwordInput).sendKeys(password);
        waitForClickable(signInButton).click();
    }

    public void signUpWithRandomCredentials() {
        String randomEmail = "testuser_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
        ((JavascriptExecutor) driver).executeScript(
            "document.querySelector('.vm--overlay').click();"
        );
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        waitForClickable(signUpTopButton).click();
        waitForElement(By.cssSelector(".vm--modal"));
        waitForElement(signUpEmail).sendKeys(randomEmail);
        waitForElement(signUpPassword).sendKeys("TestPass123!");
        waitForClickable(registerButton).click();
    }

    public void logout() {
        waitForClickable(logoutButton).click();
    }

    public boolean isLoggedIn() {
        try {
            Thread.sleep(500);
            return driver.findElement(logoutButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        return waitForElement(errorMessage).getText();
    }

    public String getLoginMessage() {
        return waitForElement(loginMessage).getText();
    }
}
