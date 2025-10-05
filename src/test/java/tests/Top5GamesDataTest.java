package tests;

import base.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.Login;
import pages.Top5GamesData;
import utils.WaitUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Top5GamesDataTest extends BaseTest {

    private Login loginPage;
    private Top5GamesData Top5GamesData;

    @BeforeMethod
    public void setUpTest() throws InterruptedException {
        loginPage = new Login(driver);
        loginPage.login("admin", "admin123");
        WaitUtil.waitForElementToLoad();
        Top5GamesData = new Top5GamesData(driver);
    }

    @Test
    public void verifyTop5GamesMatchJson() throws Exception {
        // 1. Get actual Top-5 games from UI
        List<Top5GamesData.GameInfo> uiGames = Top5GamesData.getTop5GamesDetails();

        // 2. Load expected Top-5 games from JSON
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("testdata/top-games.json");   // relative to project root
        if (!file.exists()) {
            throw new RuntimeException("JSON file not found at: " + file.getAbsolutePath());
        }

        JsonNode root = mapper.readTree(file);
        List<Top5GamesData.GameInfo> expectedGames = new ArrayList<>();

        for (JsonNode node : root.get("topGames")) {
            expectedGames.add(new Top5GamesData.GameInfo(
                    node.get("rank").asInt(),
                    node.get("game_name").asText(),
                    node.get("platform").asText(),
                    node.get("total_revenue").asDouble()
            ));
        }

        // 3. Validate counts
        Assert.assertEquals(uiGames.size(), expectedGames.size(),
                "Mismatch in Top-5 games count!");

        // 4. Validate each game’s details
        for (int i = 0; i < expectedGames.size(); i++) {
            Top5GamesData.GameInfo exp = expectedGames.get(i);
            Top5GamesData.GameInfo act = uiGames.get(i);

            Assert.assertEquals(act.rank, exp.rank, "Rank mismatch at index " + i);
            Assert.assertEquals(act.name, exp.name, "Name mismatch at rank " + exp.rank);
            Assert.assertEquals(act.platform, exp.platform.trim().toUpperCase(), "Platform mismatch for " + exp.name);
            Assert.assertEquals((int) act.revenue, (int) exp.revenue,
                    "Revenue mismatch for " + exp.name);
        }

        System.out.println("Top-5 games verification passed.");
    }
}
