package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P03_CartPage {
    static float totalPrice = 0;
    private final By pricesOfSelectedProductsLocator = By.xpath("//button[.='Remove']//preceding-sibling::div[@class='inventory_item_price']");
    private final By checkoutButton = By.id("checkout");

    private final WebDriver driver;

    public P03_CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTotalPrice() {
        try {
            List<WebElement> pricesOfSelectedProducts = driver.findElements(pricesOfSelectedProductsLocator);
            for (int i = 1; i <= pricesOfSelectedProducts.size(); i++) {
                By elements = By.xpath("(//button[.='Remove']//preceding-sibling::div[@class='inventory_item_price'])[" + i + "]");
                String fullText = Utility.getText(driver, elements);
                totalPrice += Float.parseFloat(fullText.replace("$", ""));
            }
            LogsUtils.logInfo("Total price: " + totalPrice);

            return String.valueOf(totalPrice);
        } catch (Exception e) {
            LogsUtils.logError(e.getMessage());
            return "0";
        }

    }

    public Boolean comparingPrices(String price) {
        //return getTotalPrice().equals(new P02_LandingPage(driver).getTotalPriceOfSelectedProducts());
        return getTotalPrice().equals(price);
    }

    public P04_CheckoutPage clickOnCheckoutButton() {
        Utility.clickOnElement(driver, checkoutButton);
        return new P04_CheckoutPage(driver);
    }
}
