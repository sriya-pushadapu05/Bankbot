package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static String captureScreenshot(WebDriver driver, String testName) {

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String path = "screenshots/" + testName + "_" + timestamp + ".png";

        File dest = new File(path);

        try {
            Files.createDirectories(dest.getParentFile().toPath());
            Files.copy(src.toPath(), dest.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }
}