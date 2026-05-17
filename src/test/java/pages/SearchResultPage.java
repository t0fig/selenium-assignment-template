package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class SearchResultPage extends BasePage {
    private final By bookPreviews = By.className("preview");
    private final By bookTitles = By.className("preview-title");
    private final By shopContent = By.className("shop-content");

    public SearchResultPage(WebDriver driver) {
        super(driver);
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
    }

    public List<String> getBookTitles() {
        return driver.findElements(bookTitles).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public int getResultCount() {
        return driver.findElements(bookPreviews).size();
    }

    public boolean hasResults() {
        return getResultCount() > 0;
    }
}
