package tests;

import config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import pages.BookDetailPage;
import pages.MainPage;

public class NavigationTest extends BaseTest {

    @Test
    public void testPageTitle() {
        MainPage mainPage = new MainPage(driver).open();
        Assert.assertEquals("Danube WebShop", mainPage.getPageTitle());
    }

    @Test
    public void testStaticPageContent() {
        MainPage mainPage = new MainPage(driver).open();
        String body = mainPage.getBodyText();
        Assert.assertTrue("Page should display book listings",
                body.contains("Haben oder haben"));
        Assert.assertTrue("Page should show special offer",
                body.contains("SPECIAL OFFER"));
    }

    @Test
    public void testBrowserHistoryNavigation() {
        MainPage mainPage = new MainPage(driver).open();
        String homeUrl = driver.getCurrentUrl();

        driver.get(TestConfig.getBaseUrl() + "/books/1");
        BookDetailPage detailPage = new BookDetailPage(driver);
        String bookTitle = detailPage.getBookTitle();
        Assert.assertFalse("Book title should not be empty", bookTitle.isEmpty());

        driver.navigate().back();
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        Assert.assertTrue("Should be back on main page",
                driver.getCurrentUrl().equals(homeUrl) || mainPage.isShopContentVisible());

        driver.navigate().forward();
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        Assert.assertTrue("Should return to book detail page",
                driver.getCurrentUrl().contains("/books/"));
    }

    @Test
    public void testCategoryNavigationWithComplexXPath() {
        MainPage mainPage = new MainPage(driver).open();

        // Complex XPath: navigate sidebar category using nested list structure
        By scifiCategory = By.xpath("//ul[@class='sidebar-list']//li/a[@name='scifi']");
        driver.findElement(scifiCategory).click();
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        String body = mainPage.getBodyText();
        Assert.assertTrue("Sci-fi books should be displayed",
                body.contains("1498") || body.contains("Celsius") || body.contains("Laughterhouse"));
    }

    @Test
    public void testComplexXPathSelectors() {
        MainPage mainPage = new MainPage(driver).open();
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

        // Complex XPath: find book price by navigating from title through ancestor
        By bookByTitle = By.xpath("//li[@class='preview']/div[@class='preview-title'][contains(text(),'Haben')]/following-sibling::div[@class='preview-details']/p[@class='preview-price']");
        WebElement priceElement = driver.findElement(bookByTitle);
        Assert.assertTrue("Should find price for Haben book", priceElement.getText().contains("9.95"));

        // Complex XPath: find all books with specific price
        By allBooksWithPrice = By.xpath("//li[@class='preview']//p[@class='preview-price'][contains(text(),'9.95')]");
        int count = driver.findElements(allBooksWithPrice).size();
        Assert.assertTrue("Should find multiple books at $9.95", count > 1);

        // Complex XPath: find book title by author name using sibling axis
        By bookByAuthor = By.xpath("//li[@class='preview']/div[@class='preview-author'][contains(text(),'Eromm')]/preceding-sibling::div[@class='preview-title']");
        WebElement authorBook = driver.findElement(bookByAuthor);
        Assert.assertEquals("Haben oder haben", authorBook.getText());
    }

    @Test
    public void testJavaScriptExecutorScroll() {
        MainPage mainPage = new MainPage(driver).open();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        Long scrollPosition = (Long) js.executeScript("return window.pageYOffset;");
        Assert.assertTrue("Page should have scrolled down", scrollPosition > 0);
    }

    @Test
    public void testMultiplePagesIteration() {
        String[] bookUrls = {"/books/1", "/books/2", "/books/3", "/books/4", "/books/5"};
        for (String url : bookUrls) {
            driver.get(TestConfig.getBaseUrl() + url);
            BookDetailPage detailPage = new BookDetailPage(driver);
            String title = detailPage.getBookTitle();
            Assert.assertFalse("Book at " + url + " should have a title", title.isEmpty());
        }
    }
}
