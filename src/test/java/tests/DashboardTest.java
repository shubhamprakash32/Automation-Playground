package tests;

import base.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.Dashboard;
import pages.Login;
import utils.WaitUtil;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class DashboardTest extends BaseTest {

    private Login loginPage;
    private Dashboard dashboard;

    @BeforeMethod
    public void setUpTest() throws InterruptedException {
        loginPage = new Login(driver);
        loginPage.login("admin", "admin123");
        WaitUtil.waitForElementToLoad();
        dashboard = new Dashboard(driver);
    }

    @Test
    public void verifyTotalGamesCount() {

        // Validate game count on UI
        int uiGameCount = dashboard.getGameCount();
        Assert.assertEquals(uiGameCount, 20, "Total number of games should be 20!");
    }

    @Test
    public void verifyUiGamesExistInJson() throws Exception {

        // Step 1: Get all game names from the UI
        List<String> uiGameNames = dashboard.getDisplayedGameNames();

        // Step 2: Read all game names from JSON
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(new File("testdata/sales-data.json"));
        JsonNode salesData = root.path("salesData");

        Set<String> jsonGameNames = new HashSet<>();
        for (JsonNode node : salesData) {
            jsonGameNames.add(node.path("game_name").asText().trim());
        }

        // Step 3: Verify each UI game exists in JSON
        List<String> missingGames = uiGameNames.stream()
                .filter(name -> !jsonGameNames.contains(name))
                .collect(Collectors.toList());

        if (!missingGames.isEmpty()) {
            Assert.fail("Missing games in JSON: " + missingGames);
        } else {
            System.out.println("All UI game names are present in JSON data.");
        }
    }

    @Test
    public void verifyRevenueCurrencySymbol() {

        // Step 1: Fetch all revenue values from UI
        List<String> revenueValues = dashboard.getDisplayedRevenueValues();

        // Step 2: Collect any values that don’t start with "$"
        List<String> invalidValues = revenueValues.stream()
                .filter(value -> !value.startsWith("$"))
                .collect(Collectors.toList());

        // Step 3: Assert result
        if (!invalidValues.isEmpty()) {
            Assert.fail("Revenue values are missing '$' symbol: " + invalidValues);
        } else {
            System.out.println("Revenue values correctly use '$' currency symbol.");
        }
    }

    @Test
    public void verifyDateFormatIsDDMMYYYY() {

        List<String> uiDates = dashboard.getDisplayedDates();
        System.out.println("Dates from UI: " + uiDates);

        // Regex for DD/MM/YYYY
        String datePattern = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";

        List<String> invalidDates = uiDates.stream()
                .filter(date -> !date.matches(datePattern))
                .collect(Collectors.toList());

        if (!invalidDates.isEmpty()) {
            Assert.fail("Invalid date format(s) found (not DD/MM/YYYY): " + invalidDates);
        } else {
            System.out.println("All date values follow DD/MM/YYYY format.");
        }
    }

}
