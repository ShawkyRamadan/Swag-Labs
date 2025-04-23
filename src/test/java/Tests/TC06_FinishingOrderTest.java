package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.*;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static DriverFactory.DriverFactory.*;
import static Utilities.Utility.getTimestamp;
import static Utilities.Utility.implicitlyWait;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})

public class TC06_FinishingOrderTest {
    private final String USERNAME = DataUtils.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtils.getJsonData("validLogin", "password");
    private final String FIRSTNAME = DataUtils.getJsonData("INFORMATION", "fName") + "-" + getTimestamp();
    private final String LASTNAME = DataUtils.getJsonData("INFORMATION", "lName") + "-" + getTimestamp();
    private final String ZIPCODE = new Faker().number().digits(5);

    public TC06_FinishingOrderTest() throws FileNotFoundException {
    }


    @BeforeMethod
    public void setUp() throws IOException {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : DataUtils.getPropertyData("environment", "Browser");
        setUpDriver(browser);
        LogsUtils.logInfo(browser + " is opened");
        getDriver().get(DataUtils.getPropertyData("environment", "BASE_URL"));
        LogsUtils.logInfo("The page redirect to the URL");
        implicitlyWait(getDriver(), 10);

    }

    @Test
    public void finishOrderTC() throws IOException {
        //TODO:Login Steps
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLoginButton();
        //TODO:Adding Products Steps
        new P02_LandingPage(getDriver())
                .addRandomProducts(2, 6)
                .clickOnCartIcon();
        //TODO:Go to checkout page Steps
        new P03_CartPage(getDriver())
                .clickOnCheckoutButton();
        //TODO:filling Information Form Steps
        new P04_CheckoutPage(getDriver())
                .fillingInformationForm(FIRSTNAME, LASTNAME, ZIPCODE)
                .clickOnContinueButton();
        //TODO:Go To Finishing Order Page Steps
        new P05_OverviewPage(getDriver()).clickOnFinishButton();


        Assert.assertTrue(new P06_FinishingOrderPage(getDriver()).checkVisibilityOfThanksMessage());
    }

    @AfterMethod
    public void quit() {
        quitDriver();

    }
}
