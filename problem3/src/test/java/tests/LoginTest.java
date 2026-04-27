package tests;

import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import utils.ConfigReader;

public class LoginTest extends BaseTest {

    ConfigReader config = new ConfigReader();

    // ✅ DataProvider (MANDATORY)
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][] {
            {config.getUsername(), config.getPassword(), true},
            {"wrongUser", "wrongPass", false}
        };
    }

    // ✅ Combined login test (valid + invalid)
    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password, boolean isValid) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        if (isValid) {
            Assert.assertTrue(driver.getTitle().contains("Guru99 Bank"),
                    "Valid login failed");
        } else {
            Alert alert = loginPage.waitForAlert();
            Assert.assertTrue(alert.getText().toLowerCase().contains("not valid"),
                    "Invalid login message not displayed");
            alert.accept();
        }
    }

    // ✅ Screenshot failure test (CORRECT WAY)
    @Test
    public void testInvalidLogin_ShouldFail_AndCaptureScreenshot() {

        LoginPage loginPage = new LoginPage(driver);

        // Step 1: Perform invalid login
        loginPage.login("wrongUser", "wrongPass");

        // Step 2: Wait for alert
        Alert alert = loginPage.waitForAlert();
        String alertText = alert.getText();

        // ❗ Intentionally WRONG assertion → causes REAL failure
        Assert.assertTrue(alertText.contains("Login Successful"),
                "Intentional failure to trigger screenshot");

        // Step 3: Accept alert
        alert.accept();
    }

    // ✅ Invalid login validation (for report)
    @Test
    public void testInvalidLoginAlert() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("wrongUser", "wrongPass");

        Alert alert = loginPage.waitForAlert();

        String alertText = alert.getText();
        Assert.assertTrue(alertText.toLowerCase().contains("not valid"),
                "Error message not displayed for invalid login");

        alert.accept();
    }
}