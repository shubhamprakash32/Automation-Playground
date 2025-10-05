package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.Login;
import pages.PlatformAndTotals;
import utils.WaitUtil;

import java.util.HashMap;
import java.util.Map;

public class PlatformAndTotalsTest extends BaseTest {

    private Login loginPage;
    private PlatformAndTotals platformAndTotals;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        loginPage = new Login(driver);
        loginPage.login("admin", "admin123");
        WaitUtil.waitForElementToLoad();
        platformAndTotals = new PlatformAndTotals(driver);
    }

    @Test
    public void verifyPlatformStatsAndGrandTotals() {
        // Expected data
        Map<String, PlatformAndTotals.PlatformData> expected = new HashMap<>();
        expected.put("XBOX", new PlatformAndTotals.PlatformData(5, 3790000));
        expected.put("STEAM", new PlatformAndTotals.PlatformData(5, 8680000));
        expected.put("NINTENDO", new PlatformAndTotals.PlatformData(5, 5600000));
        expected.put("PLAYSTATION", new PlatformAndTotals.PlatformData(5, 6930000));

        int expectedGrandGames = 20;
        long expectedGrandRevenue = 25000000L;

        // Actual data
        Map<String, PlatformAndTotals.PlatformData> actual = platformAndTotals.getPlatformStats();
        int actualGrandGames = platformAndTotals.getTotalGames();
        long actualGrandRevenue = platformAndTotals.getTotalRevenue();

        // Verify each platform
        for (String platform : expected.keySet()) {
            Assert.assertTrue(actual.containsKey(platform), "Missing platform: " + platform);

            PlatformAndTotals.PlatformData exp = expected.get(platform);
            PlatformAndTotals.PlatformData act = actual.get(platform);

            Assert.assertEquals(act.games, exp.games, "Games count mismatch for " + platform);
            Assert.assertEquals(act.revenue, exp.revenue, "Revenue mismatch for " + platform);
        }

        // Verify grand totals
        Assert.assertEquals(actualGrandGames, expectedGrandGames, "Total games mismatch!");
        Assert.assertEquals(actualGrandRevenue, expectedGrandRevenue, "Total revenue mismatch!");

        System.out.println("Platform stats and grand totals match expected values!");
    }
}
