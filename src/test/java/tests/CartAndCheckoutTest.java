package tests;

import config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import pages.*;

public class CartAndCheckoutTest extends BaseTest {

    @Test
    public void testAddBookToCart() {
        MainPage mainPage = new MainPage(driver).open();
        BookDetailPage detailPage = mainPage.clickBook(0);
        detailPage.addToCart();

        CartPage cartPage = new CartPage(driver).open();
        Assert.assertFalse("Cart should have at least one item",
                cartPage.getCartItems().isEmpty());
    }

    @Test
    public void testEmptyCart() {
        MainPage mainPage = new MainPage(driver).open();
        BookDetailPage detailPage = mainPage.clickBook(0);
        detailPage.addToCart();

        CartPage cartPage = new CartPage(driver).open();
        Assert.assertFalse(cartPage.getCartItems().isEmpty());
        cartPage.emptyCart();
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}

        Assert.assertTrue("Cart should be empty after clearing",
                cartPage.getCartItems().isEmpty());
    }

    @Test
    public void testFullCheckoutFlow() {
        MainPage mainPage = new MainPage(driver).open();
        mainPage.clickLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestConfig.getUserEmail(), TestConfig.getUserPassword());

        mainPage.open();
        BookDetailPage detailPage = mainPage.clickBook(0);
        detailPage.addToCart();

        CartPage cartPage = new CartPage(driver).open();
        CheckoutPage checkoutPage = cartPage.goToCheckout();

        checkoutPage.fillShippingInfo("John", "Doe", "123 Main St", "10001", "New York");
        checkoutPage.fillShippingCompany("Test Corp");
        checkoutPage.selectAsapDelivery();
        checkoutPage.submitOrder();

        String confirmation = checkoutPage.getOrderConfirmation();
        Assert.assertTrue("Order confirmation should be displayed",
                confirmation.contains("Thank you"));
    }

    @Test
    public void testCheckoutWithDifferentBillingAddress() {
        MainPage mainPage = new MainPage(driver).open();
        BookDetailPage detailPage = mainPage.clickBook(1);
        detailPage.addToCart();

        CartPage cartPage = new CartPage(driver).open();
        CheckoutPage checkoutPage = cartPage.goToCheckout();

        checkoutPage.fillShippingInfo("Bob", "Brown", "789 Pine Rd", "30003", "Chicago");
        checkoutPage.fillShippingCompany("Brown Inc");
        checkoutPage.selectSingleDelivery();
        checkoutPage.checkBillingDifferent();
        checkoutPage.fillBillingInfo("Robert", "Brown", "100 Corp Blvd", "30004", "Chicago");
        checkoutPage.submitOrder();

        String confirmation = checkoutPage.getOrderConfirmation();
        Assert.assertTrue("Order should complete with different billing",
                confirmation.contains("Thank you"));
    }
}
