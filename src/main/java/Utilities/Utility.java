package Utilities;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class Utility {
    private static final String SCREENSHOTS_PATH = "test-outputs/Screenshots/";

    /**
     * Utility function for finding, clicking on elements after waiting to be clickable
     *
     * @param driver
     * @param locator
     * @author shawky
     */

    //TODO: clicking on element
    public static void clickOnElement(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(locator));
        driver.findElement(locator).click();

    }

    //TODO:sending data to element
    public static void sendText(WebDriver driver, By locator, String text) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).sendKeys(text);
    }

    //TODO:implicitlyWait
    public static void implicitlyWait(WebDriver driver, long seconds) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
    }

    //TODO:general Wait
    public static WebDriverWait generalWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    //TODO: getTimestamp
    public static String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd-h-m-ssa").format(new Date());
    }

    //TODO: Taking screenshots
    public static void takingScreenshot(WebDriver driver, String screenshotName) throws IOException {

        try {
            File screenSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            File screenDes = new File(SCREENSHOTS_PATH + screenshotName + "-" + getTimestamp() + ".png");
            FileUtils.copyFile(screenSrc, screenDes);
            Allure.addAttachment(screenshotName, Files.newInputStream(Path.of(screenDes.getPath())));
        } catch (Exception e) {
            LogsUtils.logError(e.getMessage());
        }


    }

    //TODO: AShot takes full-page screenshots
    public static void takingFullScreenShotsUsingAShot(WebDriver driver) throws IOException {
        Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(100))
                .takeScreenshot(driver);

        ImageIO.write(screenshot.getImage(), "PNG", new File("fullpage.png"));
    }

    //TODO: Taking  Full screenshots Using Shutter Bug
    public static void takingFullScreenshotUsingShutterBug(WebDriver driver, By locator) {

        try {
            Shutterbug.shootPage(driver, Capture.FULL_SCROLL)
                    .highlight(findWebElement(driver, locator), Color.blue, 3)
                    .save(SCREENSHOTS_PATH);
        } catch (Exception e) {
            LogsUtils.logError(e.getMessage());
        }

    }

    //TODO:get text
    public static String getText(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).getText();
    }

    //TODO: scrolling to element
    public static void scrollingUsingJs(WebDriver driver, By locator) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", findWebElement(driver, locator));

    }

    //TODO: converting by to web element
    public static WebElement findWebElement(WebDriver driver, By locator) {
        return driver.findElement(locator);
    }

    //TODO: selecting from drop down
    public static void selectingFromDropDown(WebDriver driver, By locator, String option) {
        new Select(findWebElement(driver, locator)).selectByVisibleText(option);
    }

    //TODO: Generate Random Number
    public static int generateRandomNumber(int upperBound) {
        return new Random().nextInt(upperBound) + 1;

    }

    /**
     * new Random().nextInt(upperBound) generates a random number from 0 (inclusive) to upperBound (exclusive).
     * Adding +1 shifts the range to 1 (inclusive) to upperBound (inclusive).
     */
    //TODO: Generate Unique Number
    public static Set<Integer> generateUniqueNumber(int numberOfProductsNeeded, int totalNumberOfProducts) {

        Set<Integer> generatedNumbers = new HashSet<>();
        while (generatedNumbers.size() < numberOfProductsNeeded) {
            int randomNumber = generateRandomNumber(totalNumberOfProducts);
            generatedNumbers.add(randomNumber);

        }
        return generatedNumbers;

    }

    //TODO: Verify URL
    public static Boolean verifyURL(WebDriver driver, String expectedURL) {
        try {
            Utility.generalWait(driver).until(ExpectedConditions.urlToBe(expectedURL));

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //TODO: Get Latest File
    public static File getLatestFile(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        assert files != null;
        if (files.length == 0) {
            return null;
        } else {
            Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
            return files[0];
        }


    }

    //TODO: Get All Cookies
    public static Set<Cookie> getAllCookies(WebDriver driver) {

        return driver.manage().getCookies();
    }

    //TODO: Restore Session
    public static void restoreSession(WebDriver driver, Set<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }

    }
    //TODO: uploading files using ROBOT

}
