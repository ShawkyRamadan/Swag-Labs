package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static DriverFactory.DriverFactory.*;
import static Utilities.Utility.implicitlyWait;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})

public class TC01_LoginTest {

    private final String USERNAME = DataUtils.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtils.getJsonData("validLogin", "password");

    public TC01_LoginTest() throws FileNotFoundException {
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
    public void validLoginTC() throws IOException {
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLoginButton();
        Assert.assertTrue(new P01_LoginPage(getDriver()).assertLoginTC(DataUtils.getPropertyData("environment", "HOME_URL")));

    }

    @AfterMethod
    public void quit() {
        quitDriver();

    }


}
