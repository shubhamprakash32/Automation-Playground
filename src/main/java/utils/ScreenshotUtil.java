package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class ScreenshotUtil {

    // Method to capture screenshot
    public static void capture(WebDriver driver, String fileName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            // Make sure screenshots folder exists
            File destination = new File("screenshots/" + fileName + ".png");
            FileUtils.copyFile(src, destination);
            System.out.println("Screenshot saved: " + destination.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}
