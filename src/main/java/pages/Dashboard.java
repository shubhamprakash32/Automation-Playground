package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import java.util.stream.Collectors;

public class Dashboard {
    private final WebDriver driver;

    @FindBy(xpath = "//div[@class='sales-table-container']/table/tbody/tr")
    private List<WebElement> gameRows;

    @FindBy(xpath = "//div[@class='sales-table-container']/table/tbody/tr/td[2]")
    private List<WebElement> gameNames;

    @FindBy(xpath = "//div[@class='sales-table-container']/table/tbody/tr/td[4]")
    private List<WebElement> revenueCells;

    @FindBy(xpath = "//div[@class='sales-table-container']/table/tbody/tr/td[5]")
    private List<WebElement> dateCells;



    public Dashboard(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Count number of games in Sales Data
    public int getGameCount() {
        return gameRows.size();
    }

    // Get all game names displayed on the UI
    public List<String> getDisplayedGameNames() {
        return gameNames.stream()
                .map(e -> e.getText().trim())
                .collect(Collectors.toList());
    }

    public List<String> getDisplayedRevenueValues() {
        return revenueCells.stream()
                .map(e -> e.getText().trim())
                .collect(Collectors.toList());
    }

    public List<String> getDisplayedDates() {
        return dateCells.stream()
                .map(e -> e.getText().trim())
                .collect(Collectors.toList());
    }
}
