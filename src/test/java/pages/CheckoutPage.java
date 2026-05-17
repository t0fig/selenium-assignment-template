package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutPage extends BasePage {
    private final By checkoutContent = By.className("checkout");
    // Shipping fields
    private final By shippingName = By.id("s-name");
    private final By shippingSurname = By.id("s-surname");
    private final By shippingAddress = By.id("s-address");
    private final By shippingZipcode = By.id("s-zipcode");
    private final By shippingCity = By.id("s-city");
    private final By shippingCompany = By.id("s-company");
    // Radio buttons for delivery
    private final By radioAsap = By.id("asap");
    private final By radioSingle = By.id("single");
    // Checkbox
    private final By billingDifferent = By.id("billing-different");
    // Billing fields (shown when billing-different is checked)
    private final By billingName = By.id("b-name");
    private final By billingSurname = By.id("b-surname");
    private final By billingAddress = By.id("b-address");
    private final By billingZipcode = By.id("b-zipcode");
    private final By billingCity = By.id("b-city");
    // Submit
    private final By buyButton = By.xpath("//button[text()='Buy']");
    // Confirmation
    private final By orderConfirmation = By.id("order-confirmation");

    public CheckoutPage(WebDriver driver) {
        super(driver);
        waitForElement(checkoutContent);
    }

    public void fillShippingInfo(String name, String surname, String address, String zipcode, String city) {
        waitForElement(shippingName).sendKeys(name);
        driver.findElement(shippingSurname).sendKeys(surname);
        driver.findElement(shippingAddress).sendKeys(address);
        driver.findElement(shippingZipcode).sendKeys(zipcode);
        driver.findElement(shippingCity).sendKeys(city);
    }

    public void fillShippingCompany(String company) {
        driver.findElement(shippingCompany).sendKeys(company);
    }

    public void selectAsapDelivery() {
        clickViaJs(driver.findElement(radioAsap));
    }

    public void selectSingleDelivery() {
        clickViaJs(driver.findElement(radioSingle));
    }

    public void checkBillingDifferent() {
        clickViaJs(driver.findElement(billingDifferent));
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }

    public void fillBillingInfo(String name, String surname, String address, String zipcode, String city) {
        waitForElement(billingName).sendKeys(name);
        driver.findElement(billingSurname).sendKeys(surname);
        driver.findElement(billingAddress).sendKeys(address);
        driver.findElement(billingZipcode).sendKeys(zipcode);
        driver.findElement(billingCity).sendKeys(city);
    }

    public void submitOrder() {
        WebElement btn = driver.findElement(buyButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn);
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public String getOrderConfirmation() {
        return waitForElement(orderConfirmation).getText();
    }

    private void clickViaJs(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
