package pages;

import config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MainPage extends BasePage {
    private final By loginButton = By.id("login");
    private final By searchInput = By.cssSelector("input[name='searchbar']");
    private final By searchButton = By.id("button-search");
    private final By bookPreviews = By.className("preview");
    private final By shopContent = By.className("shop-content");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public MainPage open() {
        driver.get(TestConfig.getBaseUrl());
        waitForElement(shopContent);
        return this;
    }

    public void clickLoginButton() {
        waitForClickable(loginButton).click();
    }

    public SearchResultPage searchFor(String query) {
        WebElement input = waitForElement(searchInput);
        input.clear();
        input.sendKeys(query);
        waitForClickable(searchButton).click();
        return new SearchResultPage(driver);
    }

    public BookDetailPage clickBook(int index) {
        List<WebElement> books = driver.findElements(bookPreviews);
        books.get(index).click();
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        return new BookDetailPage(driver);
    }

    public BookDetailPage clickBookByTitle(String title) {
        By bookLocator = By.xpath("//div[@class='preview-title'][text()='" + title + "']/ancestor::div[@class='preview']");
        waitForClickable(bookLocator).click();
        return new BookDetailPage(driver);
    }

    public void clickCategory(String categoryName) {
        By categoryLink = By.xpath("//ul[@class='sidebar-list']//a[@name='" + categoryName + "']");
        waitForClickable(categoryLink).click();
        waitForElement(shopContent);
    }

    public List<WebElement> getDisplayedBooks() {
        return driver.findElements(bookPreviews);
    }

    public boolean isShopContentVisible() {
        try {
            return waitForElement(shopContent).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
