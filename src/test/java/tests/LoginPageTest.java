package tests;

import Utils.SoftAssertUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class LoginPageTest {

    @Test
    public void testLoginFields() {
        // Step 1: Get Singleton instance
        SoftAssertUtil softAssert = SoftAssertUtil.getInstance();

        // Step 2: Perform soft assertions
        softAssert.assertEquals("Actual", "Expected", "Title mismatch");
        softAssert.assertTrue(false, "Checkbox is not selected");
        softAssert.assertNotNull(null, "User ID should not be null");
    }

    @AfterMethod
    public void tearDown() {
        // Step 3: Final call to assertAll() to evaluate and throw if any assertion failed
        SoftAssertUtil.getInstance().assertAll();
    }
}

