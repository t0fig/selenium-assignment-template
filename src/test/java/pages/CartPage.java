package pages;

import config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {
    private final By cartIcon = By.id("cart");
    private final By cartItems = By.xpath("//div[contains(@class,'cart')]//li");
    private final By totalPrice = By.id("total-price");
    private final By emptyCartButton = By.xpath("//button[contains(text(),'Empty')]");
    private final By checkoutButton = By.xpath("//button[contains(text(),'Checkout')]");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CartPage open() {
        driver.get(TestConfig.getBaseUrl() + "/cart");
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
        return this;
    }

    public CartPage openViaIcon() {
        waitForClickable(cartIcon).click();
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
        return this;
    }

    public List<WebElement> getCartItems() {
        return driver.findElements(cartItems);
    }

    public String getTotalPrice() {
        return waitForElement(totalPrice).getText();
    }

    public void emptyCart() {
        waitForClickable(emptyCartButton).click();
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }

    public CheckoutPage goToCheckout() {
        waitForClickable(checkoutButton).click();
        return new CheckoutPage(driver);
    }
}
