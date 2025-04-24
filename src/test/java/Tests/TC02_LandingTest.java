package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import static DriverFactory.DriverFactory.*;
import static Utilities.Utility.implicitlyWait;


@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})

public class TC02_LandingTest {

    private final String USERNAME = DataUtils.getJsonData("validLogin", "username");
    private final String PASSWORD = DataUtils.getJsonData("validLogin", "password");
    private Set<Cookie> cookies;

    public TC02_LandingTest() throws FileNotFoundException {
    }


    @BeforeClass(alwaysRun = true)
    public void loginCookie() throws IOException {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : DataUtils.getPropertyData("environment", "Browser");
        setUpDriver(browser);
        LogsUtils.logInfo(browser + " is opened");
        getDriver().get(DataUtils.getPropertyData("environment", "BASE_URL"));
        LogsUtils.logInfo("The page redirect to the URL");
        implicitlyWait(getDriver(), 10);
        new P01_LoginPage(getDriver())
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLoginButton();
        cookies = Utility.getAllCookies(getDriver());
        quitDriver();

    }


    @BeforeMethod(alwaysRun = true)
    public void setUp() throws IOException {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : DataUtils.getPropertyData("environment", "Browser");
        setUpDriver(browser);
        LogsUtils.logInfo(browser + " is opened");
        getDriver().get(DataUtils.getPropertyData("environment", "BASE_URL"));
        LogsUtils.logInfo("The page redirect to the URL");
        Utility.restoreSession(getDriver(), cookies);
        getDriver().get(DataUtils.getPropertyData("environment", "HOME_URL"));
        getDriver().navigate().refresh();

    }

    @Test
    public void checkingNumberOfSelectedProductsTC() {

        new P02_LandingPage(getDriver()).addAllProductsToCart();
        // Assert.assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductsWithCart());
        Assert.assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductsWithCart());

    }

    @Test
    public void addingRandomProductsToCartTC() {
        new P02_LandingPage(getDriver())
                .addRandomProducts(3, 6);
        Assert.assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductsWithCart());
    }

    @Test
    public void clickOnCartIconTC() throws IOException {
        new P02_LandingPage(getDriver())
                .addRandomProducts(2, 6)
                .clickOnCartIcon();
        Assert.assertTrue(Utility.verifyURL(getDriver(), DataUtils.getPropertyData("environment", "CART_URL")));
    }

    @AfterMethod
    public void quit() {
        quitDriver();

    }

    @AfterClass(alwaysRun = true)
    public void deleteSession() {
        cookies.clear();
    }
}
