package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BookDetailPage extends BasePage {
    private final By detailWrapper = By.className("detail-wrapper");
    private final By addToCartButton = By.xpath("//button[contains(text(),'Add to cart')]");
    private final By bookTitle = By.xpath("//div[@class='detail-text-content']//h2");
    private final By bookRating = By.className("book-rating");

    public BookDetailPage(WebDriver driver) {
        super(driver);
        waitForElement(detailWrapper);
    }

    public String getBookTitle() {
        return waitForElement(bookTitle).getText();
    }

    public String getBookRating() {
        return waitForElement(bookRating).getText();
    }

    public void addToCart() {
        waitForClickable(addToCartButton).click();
    }
}
