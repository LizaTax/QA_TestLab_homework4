package myprojects.automation.assignment4.tests;

import myprojects.automation.assignment4.BaseTest;
import myprojects.automation.assignment4.model.ProductData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateProductTest extends BaseTest {
    private static final String baseUrl = "http://prestashop-automation.qatestlab.com.ua";
    private static ProductData PRODUCT = null;

    @DataProvider
    public Object[][] getLoginData(){
        return new String[][]{
                {"webinar.test@gmail.com", "Xcg7299bnSmMuRLp9ITw"}
        };
    }
    @Test(dataProvider = "getLoginData")
    public void createNewProduct(String email, String password) throws InterruptedException {
        actions.openUrl(baseUrl + "/admin147ajyvk0/");
        actions.login(email, password);
        PRODUCT = ProductData.generate();
        actions.createProduct(PRODUCT);
    }

    @Test(dependsOnMethods = "createNewProduct")
    public void checkProductVisibility() {
        actions.openUrl(baseUrl + "/ru/");
        actions.checkProductVisibility(PRODUCT);
    }
}
