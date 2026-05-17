package tests;

import org.junit.Assert;
import org.junit.Test;
import pages.MainPage;
import pages.SearchResultPage;

public class SearchTest extends BaseTest {

    @Test
    public void testSearchByTitle() {
        MainPage mainPage = new MainPage(driver).open();
        SearchResultPage results = mainPage.searchFor("Haben");

        Assert.assertTrue("Search should return results for 'Haben'",
                results.hasResults());
        Assert.assertTrue("Results should contain the matching book",
                results.getBookTitles().stream().anyMatch(t -> t.contains("Haben")));
    }

    @Test
    public void testSearchByAuthor() {
        MainPage mainPage = new MainPage(driver).open();
        SearchResultPage results = mainPage.searchFor("Salinger");

        Assert.assertTrue("Search should return results for author 'Salinger'",
                results.hasResults());
    }

    @Test
    public void testSearchNoResults() {
        MainPage mainPage = new MainPage(driver).open();
        SearchResultPage results = mainPage.searchFor("xyznonexistentbook123");

        Assert.assertFalse("Search should return no results for nonsense query",
                results.hasResults());
    }

    @Test
    public void testMultiplePageIteration() {
        String[] searchQueries = {"Haben", "Parry", "1498", "Grand", "Celsius"};
        for (String query : searchQueries) {
            MainPage mainPage = new MainPage(driver).open();
            SearchResultPage results = mainPage.searchFor(query);
            Assert.assertTrue("Search for '" + query + "' should return results",
                    results.hasResults());
        }
    }
}
