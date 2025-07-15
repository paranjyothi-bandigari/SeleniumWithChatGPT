package Utils;

import org.testng.asserts.SoftAssert;

/**
 * Utility class for performing soft assertions using Singleton Design Pattern.
 * It ensures that only one instance of SoftAssertUtil is used throughout the test run.
 */
public class SoftAssertUtil {

    // Step 1: Create a static instance for Singleton pattern
    private static SoftAssertUtil instance;

    // Step 2: Use ThreadLocal for thread-safe SoftAssert instance
    // This ensures that each test thread gets its own SoftAssert instance
    private static final ThreadLocal<SoftAssert> softAssertThreadLocal = ThreadLocal.withInitial(SoftAssert::new);

    // Step 3: Private constructor prevents external instantiation
    private SoftAssertUtil() {
        // Private constructor to restrict instantiation from outside the class
    }

    // Step 4: Provide a global access point to get the Singleton instance
    public static synchronized SoftAssertUtil getInstance() {
        // Lazy initialization: create instance only when first requested
        if (instance == null) {
            instance = new SoftAssertUtil();
        }
        return instance;
    }

    // Step 5: Soft assertion wrapper methods

    /**
     * Asserts that two objects are equal.
     */
    public void assertEquals(Object actual, Object expected, String message) {
        softAssertThreadLocal.get().assertEquals(actual, expected, message);
    }

    /**
     * Asserts that a condition is true.
     */
    public void assertTrue(boolean condition, String message) {
        softAssertThreadLocal.get().assertTrue(condition, message);
    }

    /**
     * Asserts that a condition is false.
     */
    public void assertFalse(boolean condition, String message) {
        softAssertThreadLocal.get().assertFalse(condition, message);
    }

    /**
     * Asserts that an object is not null.
     */
    public void assertNotNull(Object obj, String message) {
        softAssertThreadLocal.get().assertNotNull(obj, message);
    }

    // Step 6: Final call to assert all and clear the ThreadLocal

    /**
     * Invokes assertAll() to throw collected assertion errors.
     * Always call this at the end of the test method to validate all assertions.
     */
    public void assertAll() {
        softAssertThreadLocal.get().assertAll();  // throws AssertionError if any soft assertions failed
        softAssertThreadLocal.remove();  // important: remove to avoid memory leaks and stale state
    }
}
