package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlatformAndTotals {

    private final WebDriver driver;

    // Locate platform cards (Xbox, Steam, Nintendo, PlayStation)
    @FindBy(xpath = "//div[@class='platform-stat-card']")
    private List<WebElement> platformCards;

    // Top summary cards
    @FindBy(xpath = "//p[@class='revenue-amount']")
    private WebElement totalRevenueElement;

    @FindBy(xpath = "//p[@class='games-count']")
    private WebElement totalGamesElement;

    public PlatformAndTotals(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Extract platform statistics
    public Map<String, PlatformData> getPlatformStats() {
        Map<String, PlatformData> stats = new HashMap<>();

        for (WebElement card : platformCards) {
            // Platform name from the colored badge
            String platform = card.findElement(By.tagName("span")).getText().trim().toUpperCase();

            // Games count
            String gamesText = card.getText().split("\n")[1].trim(); // e.g. "Games: 5"
            int gamesCount = Integer.parseInt(gamesText.replaceAll("[^0-9]", ""));

            // Revenue
            String revenueText = card.getText().split("\n")[2].trim(); // e.g. "Revenue: $3,790,000.00"
            String cleanedRevenue = revenueText.replaceAll("[^0-9.]", "");
            long revenue = Long.parseLong(cleanedRevenue.replaceAll("\\.\\d+$", ""));

            stats.put(platform, new PlatformData(gamesCount, revenue));
        }

        return stats;
    }

    // Extract grand totals from the top summary
    public int getTotalGames() {
        return Integer.parseInt(totalGamesElement.getText().trim());
    }

    public long getTotalRevenue() {
        String revenueText = totalRevenueElement.getText().replaceAll("[$,]", "").trim();
        // Remove the decimal part (".00")
        revenueText = revenueText.split("\\.")[0];
        return Long.parseLong(revenueText);
    }

    // Inner class to store stats
    public static class PlatformData {
        public int games;
        public long revenue;

        public PlatformData(int games, long revenue) {
            this.games = games;
            this.revenue = revenue;
        }
    }
}
