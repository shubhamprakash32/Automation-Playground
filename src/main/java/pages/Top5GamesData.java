package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class Top5GamesData {

    private final WebDriver driver;

    @FindBy(xpath = "//div[@class='game-rank']")
    private List<WebElement> top5GameRanks;

    @FindBy(xpath = "//div[@class='game-info']/h4")
    private List<WebElement> top5GameNames;

    @FindBy(xpath = "//p[@class='game-platform']/span")
    private List<WebElement> top5GamePlatforms;

    @FindBy(xpath = "//div[@class='game-revenue']")
    private List<WebElement> top5GameRevenues;

    public Top5GamesData(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ----- Model class for game data -----
    public static class GameInfo {
        public int rank;
        public String name;
        public String platform;
        public double revenue;

        public GameInfo(int rank, String name, String platform, double revenue) {
            this.rank = rank;
            this.name = name;
            this.platform = platform;
            this.revenue = revenue;
        }
    }

    // ----- Helpers -----
    private double parseRevenue(String text) {
        return Double.parseDouble(text.replaceAll("[$,]", ""));
    }

    // ----- Public API -----
    public List<GameInfo> getTop5GamesDetails() {
        List<GameInfo> games = new ArrayList<>();
        for (int i = 0; i < top5GameNames.size(); i++) {
            int rank = Integer.parseInt(top5GameRanks.get(i).getText().replaceAll("[^0-9]", ""));
            String name = top5GameNames.get(i).getText().trim();
            String platform = top5GamePlatforms.get(i).getText().trim();
            double revenue = parseRevenue(top5GameRevenues.get(i).getText());

            games.add(new GameInfo(rank, name, platform, revenue));
        }
        return games;
    }

}
