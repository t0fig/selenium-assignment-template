package pages;

import config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AccountPage extends BasePage {
    private final By accountIcon = By.cssSelector(".topbar-icon .fa-user");
    private final By accountContent = By.className("account-content");
    private final By ordersSection = By.id("orders");
    private final By userDetails = By.id("user-details");
    private final By orderItems = By.xpath("//div[@id='orders']//li");

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public AccountPage open() {
        driver.get(TestConfig.getBaseUrl() + "/#/account");
        waitForElement(accountContent);
        return this;
    }

    public AccountPage openViaIcon() {
        waitForClickable(accountIcon).click();
        waitForElement(accountContent);
        return this;
    }

    public boolean isAccountPageDisplayed() {
        return waitForElement(accountContent).isDisplayed();
    }

    public List<WebElement> getOrders() {
        return driver.findElements(orderItems);
    }

    public String getUserDetails() {
        return waitForElement(userDetails).getText();
    }
}
