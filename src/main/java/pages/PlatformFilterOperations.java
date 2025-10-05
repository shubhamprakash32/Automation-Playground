package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import utils.WaitUtil;

import java.util.ArrayList;
import java.util.List;

public class PlatformFilterOperations {

    private final WebDriver driver;

    // Dropdown for platform filtering
    @FindBy(xpath = "//select[@class='platform-filter']")
    private WebElement platformDropdown;

    // Table rows (filtered results)
    @FindBy(xpath = "//div[@class='sales-table-container']/table/tbody/tr")
    private List<WebElement> tableRows;

    // Total revenue card at the top
    @FindBy(xpath = "//p[@class='revenue-amount']")
    private WebElement totalRevenueElement;

    public PlatformFilterOperations(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Select a platform filter
     */
    public void selectPlatform(String platform) {
        platformDropdown.click();
        WaitUtil.waitForVisibility(driver, platformDropdown, 10);
        WebElement option = driver.findElement(By.xpath("//option[text()='" + platform + "']"));
        option.click();
    }

    /**
     * Get all platforms shown in the table after filtering
     */
    public List<String> getDisplayedPlatforms(){
        List<String> platforms = new ArrayList<>();
        for (WebElement row : tableRows) {
            String platform = row.findElement(By.xpath("./td[3]")).getText().trim().toUpperCase();
            platforms.add(platform);
        }
        return platforms;
    }

    /**
     * Get total revenue from the top card
     */
    public long getDisplayedTotalRevenue() {
        WaitUtil.waitForVisibility(driver, totalRevenueElement, 10);
        String revenueText = totalRevenueElement.getText().replaceAll("[$,]", "").trim();
        revenueText = revenueText.split("\\.")[0];   // remove decimals
        return Long.parseLong(revenueText);
    }

    /**
     * Get all revenue values from the table for validation
     */
    public List<Long> getAllRowRevenues() {
        List<Long> revenues = new ArrayList<>();
        for (WebElement row : tableRows) {
            String revenueText = row.findElement(By.xpath("./td[4]")).getText()
                    .replaceAll("[$,]", "").trim();
            revenueText = revenueText.split("\\.")[0];
            revenues.add(Long.parseLong(revenueText));
        }
        return revenues;
    }
}
