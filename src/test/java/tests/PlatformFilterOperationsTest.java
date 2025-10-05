package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.Login;
import pages.PlatformFilterOperations;
import utils.WaitUtil;
import java.util.List;

public class PlatformFilterOperationsTest extends BaseTest {

    private Login loginPage;
    private PlatformFilterOperations PlatformFilterOperations;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        loginPage = new Login(driver);
        loginPage.login("admin", "admin123");
        WaitUtil.waitForElementToLoad();
        PlatformFilterOperations = new PlatformFilterOperations(driver);
    }

    @Test(priority = 1)
    public void verifyAllShowsAllGames() {
        PlatformFilterOperations.selectPlatform("All");
        List<String> platforms = PlatformFilterOperations.getDisplayedPlatforms();
        Assert.assertTrue(platforms.contains("XBOX"));
        Assert.assertTrue(platforms.contains("STEAM"));
        Assert.assertTrue(platforms.contains("NINTENDO"));
        Assert.assertTrue(platforms.contains("PLAYSTATION"));
    }

    @Test(priority = 2)
    public void verifyXboxFilter() {
        PlatformFilterOperations.selectPlatform("Xbox");
        List<String> platforms = PlatformFilterOperations.getDisplayedPlatforms();
        Assert.assertTrue(platforms.stream().allMatch(p -> p.equals("XBOX")),
                "All rows should belong to Xbox");

        long calculatedRevenue = PlatformFilterOperations.getAllRowRevenues().stream().mapToLong(Long::longValue).sum();
        long displayedRevenue = PlatformFilterOperations.getDisplayedTotalRevenue();
        Assert.assertEquals(displayedRevenue, calculatedRevenue, "Revenue total mismatch for Xbox");
    }

    @Test(priority = 3)
    public void verifySteamFilter() {
        PlatformFilterOperations.selectPlatform("Steam");
        List<String> platforms = PlatformFilterOperations.getDisplayedPlatforms();
        Assert.assertTrue(platforms.stream().allMatch(p -> p.equals("STEAM")),
                "All rows should belong to Steam");

        long calculatedRevenue = PlatformFilterOperations.getAllRowRevenues().stream().mapToLong(Long::longValue).sum();
        long displayedRevenue = PlatformFilterOperations.getDisplayedTotalRevenue();
        Assert.assertEquals(displayedRevenue, calculatedRevenue, "Revenue total mismatch for Steam");
    }

    @Test(priority = 4)
    public void verifyNintendoFilter() {
        PlatformFilterOperations.selectPlatform("Nintendo");
        List<String> platforms = PlatformFilterOperations.getDisplayedPlatforms();
        Assert.assertTrue(platforms.stream().allMatch(p -> p.equals("NINTENDO")),
                "All rows should belong to Nintendo");

        long calculatedRevenue = PlatformFilterOperations.getAllRowRevenues().stream().mapToLong(Long::longValue).sum();
        long displayedRevenue = PlatformFilterOperations.getDisplayedTotalRevenue();
        Assert.assertEquals(displayedRevenue, calculatedRevenue, "Revenue total mismatch for Nintendo");
    }

    @Test(priority = 5)
    public void verifyPlayStationFilter() {
        PlatformFilterOperations.selectPlatform("PlayStation");
        List<String> platforms = PlatformFilterOperations.getDisplayedPlatforms();
        Assert.assertTrue(platforms.stream().allMatch(p -> p.equals("PLAYSTATION")),
                "All rows should belong to PlayStation");

        long calculatedRevenue = PlatformFilterOperations.getAllRowRevenues().stream().mapToLong(Long::longValue).sum();
        long displayedRevenue = PlatformFilterOperations.getDisplayedTotalRevenue();
        Assert.assertEquals(displayedRevenue, calculatedRevenue, "Revenue total mismatch for PlayStation");
    }
}
