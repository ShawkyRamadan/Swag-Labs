package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P05_OverviewPage {

    private final By subTotal = By.className("summary_subtotal_label");
    private final By tax = By.className("summary_tax_label");
    private final By total = By.className("summary_total_label");
    private final By finishButton = By.id("finish");

    private final WebDriver driver;


    public P05_OverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    public float getSubTotal() {
        // LogsUtils.logInfo("subTotal: " + Utility.getText(driver, subTotal));
        return Float.parseFloat(Utility.getText(driver, subTotal).replace("Item total: $", ""));
    }

    public float getTax() {
        //LogsUtils.logInfo("tax: " + Utility.getText(driver, tax));

        return Float.parseFloat(Utility.getText(driver, tax).replace("Tax: $", ""));
    }

    public float getTotal() {
        LogsUtils.logInfo("Actual Total price: " + Utility.getText(driver, total));

        return Float.parseFloat(Utility.getText(driver, total).replace("Total: $", ""));
    }

    public String calculateTotalPrice() {
        LogsUtils.logInfo("Calculated Total price: " + (getSubTotal() + getTax()));

        return String.valueOf(getSubTotal() + getTax());
    }

    public boolean comparingPrices() {
        return String.valueOf(getTotal()).equals(calculateTotalPrice());
    }

    public P06_FinishingOrderPage clickOnFinishButton() {
        Utility.clickOnElement(driver, finishButton);
        return new P06_FinishingOrderPage(driver);
    }


}
