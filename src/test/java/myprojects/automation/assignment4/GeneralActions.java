package myprojects.automation.assignment4;


import myprojects.automation.assignment4.model.ProductData;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.Assertion;

import java.util.List;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 15);
    }

    /**
     * Logs in to Admin Panel.
     * @param login
     * @param password
     */
    /**
     * Logs in to Admin Panel.
     * @param email
     * @param password
     */
    public void login(String email, String password) {
        setEditText(By.id("email"), email);
        setEditText(By.id("passwd"), password);
        clickOnElement(By.name("submitLogin"));
    }

    public void createProduct(ProductData newProduct) throws InterruptedException {
        verifyElementAppears(By.id("subtab-AdminCatalog"));
        hoverMouse(By.id("subtab-AdminCatalog"));
        verifyElementAppears(By.id("subtab-AdminProducts"));
        clickOnElement(By.id("subtab-AdminProducts"));
        verifyElementAppears(By.id("page-header-desc-configuration-add"));
        clickOnElement(By.id("page-header-desc-configuration-add"));
        setEditText(By.id("form_step1_name_1"), newProduct.getName());
        clickOnElement(By.id("tab_step3"));
        setEditText(By.id("form_step3_qty_0"), String.valueOf(newProduct.getQty()));
        clickOnElement(By.id("tab_step2"));
        setEditText(By.id("form_step2_price"), String.valueOf(newProduct.getPrice()));
        clickOnElement(By.className("switch-input"));
        verifyGrowlMessage();
        clickOnElement(By.cssSelector(".js-btn-save[type=\"submit\"]"));
        verifyGrowlMessage();

    }
    public void checkProductVisibility(ProductData newProduct) {
        clickOnElement(By.cssSelector(".all-product-link"));
        waitForContentLoad();
        Assert.assertTrue(isElementWithTextPresent(By.cssSelector("h1.product-title"), newProduct.getName()));
        clickOnElement(By.linkText(newProduct.getName()));
        verifyElementWithTextPresent(By.cssSelector(".h1[itemprop=\"name\"]"), newProduct.getName().toUpperCase());
        verifyElementWithTextPresent(By.cssSelector(".current-price span[itemprop=\"price\"]"), newProduct.getPrice() + " ₴");
        verifyElementWithTextPresent(By.cssSelector(".product-quantities span"), String.valueOf(newProduct.getQty()) + " Товары");
    }

    /**
     * Waits until page loader disappears from the page
     */
    public void waitForContentLoad() {
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    public void openUrl(String url){
        driver.get(url);
    }

    private void clickOnElement(By by) {
        WebElement e = wait.until(ExpectedConditions.elementToBeClickable(by));
        if (e == null) {
            throw new ElementNotInteractableException(String.format("Element %s can not be clicked", by));
        }
        e.click();
    }

    private void hoverMouse(By by) {
        WebElement element = driver.findElement(by);
        String code = "var fireOnThis = arguments[0];"
                + "var evObj = document.createEvent('MouseEvents');"
                + "evObj.initEvent( 'mouseover', true, true );"
                + "fireOnThis.dispatchEvent(evObj);";
        ((JavascriptExecutor)driver).executeScript(code, element);
    }


    private void setEditText(By by, String text) {
        WebElement element = driver.findElement(by);
        if (!element.getAttribute("value").isEmpty()) {
            element.clear();
        }
        element.sendKeys(text);
    }

    private void verifyElementAppears(By by) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    private void verifyElementWithTextPresent(By by, String text) {
        WebElement element = driver.findElement(by);
        if (!element.getText().contains(text)) {
            element.findElement(By.xpath(String.format(".//*[contains(., '%s')]", text)));
        }
        verifyElementIsVisible(by);
    }

    private boolean isElementWithTextPresent(By by, String text) {
        List<WebElement> elements = driver.findElements(by);
        boolean present = false;
        for(WebElement element : elements) {
            if(element.getText().equals(text)){
                present = true;
                break;
            }
        }
        return present;
    }

    private void verifyElementIsVisible(By by) {
        if (!driver.findElement(by).isDisplayed()) {
            throw new NotFoundException("Error: element " + by + " is not visible on the page!");
        }
    }

    private void verifyGrowlMessage() {
        verifyElementAppears(By.className("growl-message"));
        clickOnElement(By.className("growl-close"));
    }
}
