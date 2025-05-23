package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P04_CheckoutPage {
    private final WebDriver driver;

    private final By firstName = By.id("first-name");

    private final By lastName = By.id("last-name");

    private final By zipCode = By.id("postal-code");

    private final By continueButton = By.id("continue");


    public P04_CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public P04_CheckoutPage fillingInformationForm(String fName, String lName, String zip) {
        Utility.sendText(driver, firstName, fName);
        LogsUtils.logInfo("FIRSTNAME: " + fName);
        Utility.sendText(driver, lastName, lName);
        LogsUtils.logInfo("LASTNAME: " + lName);
        Utility.sendText(driver, zipCode, zip);
        LogsUtils.logInfo("ZIPCODE: " + zip);
        return this;
    }

    public P05_OverviewPage clickOnContinueButton() {
        Utility.clickOnElement(driver, continueButton);
        return new P05_OverviewPage(driver);
    }
}
