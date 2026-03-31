package com.automation.selenide.base;

import com.automation.selenide.pages.CartPage;
import com.automation.selenide.pages.CheckoutCompletePage;
import com.automation.selenide.pages.CheckoutInfoPage;
import com.automation.selenide.pages.CheckoutOverviewPage;
import com.automation.selenide.pages.InventoryPage;
import com.automation.selenide.pages.LoginPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Attachment;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.open;

/**
 * Base class for all test classes.
 * <p>
 * Responsibilities:
 * <ul>
 *   <li>Configures JUL logging via {@code logging.properties} on class load.</li>
 *   <li>Configures Selenide ({@code browser}, {@code headless}, {@code baseUrl}, {@code timeout})
 *       before each test via {@link #setUp()}.</li>
 *   <li>Registers the {@link AllureSelenide} listener for automatic screenshot and
 *       page-source capture on failure.</li>
 *   <li>Instantiates all six page objects before each test.</li>
 *   <li>Closes the browser after each test via {@link #tearDown()}.</li>
 *   <li>Logs {@code [PASS]}, {@code [FAIL]}, and {@code [SKIP]} markers to the
 *       console through the inner {@link JulTestWatcher}.</li>
 * </ul>
 * </p>
 */
@ExtendWith({SetUp.JulTestWatcher.class, SetUp.ScreenshotOnFailureExtension.class})
public class SetUp {
    static {
        try {
            InputStream config = SetUp.class.getClassLoader()
                    .getResourceAsStream("logging.properties");
            if (config != null) {
                LogManager.getLogManager().readConfiguration(config);
            }

            // Ensure message-only format even if the file is missing
            System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%n");

            // Suppress Selenium and CDP DevTools log noise
            Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
            Logger.getLogger("org.openqa.selenium.devtools").setLevel(Level.OFF);
        } catch (Exception e) {
            System.err.println("Failed to load logging.properties: " + e.getMessage());
        }
    }

    // Initialization of page classes
    protected LoginPage loginPage;
    protected InventoryPage inventoryPage;
    protected CartPage cartPage;
    protected CheckoutInfoPage checkoutInfoPage;
    protected CheckoutOverviewPage checkoutOverviewPage;
    protected CheckoutCompletePage checkoutCompletePage;


    /**
     * Runs before each test method.
     * <p>
     * Configures Selenide, registers the Allure listener, opens the base URL,
     * and instantiates all page objects.
     * </p>
     */
    @BeforeEach
    public void setUp() {
        // Browser selection — defaults to Chrome; pass -Dbrowser=firefox to switch
        String browser = System.getProperty("browser", "chrome");
        Configuration.browser = browser;

        // Headless mode — pass -Dheadless=true to enable
        Configuration.headless = "true".equalsIgnoreCase(System.getProperty("headless", "false"));

        // Add Chrome options for Docker compatibility
        if ("chrome".equalsIgnoreCase(browser)) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            Configuration.browserCapabilities = chromeOptions;
        }

        Configuration.baseUrl = "https://www.saucedemo.com";
        Configuration.timeout = 10000;

        // Register Allure listener — captures screenshot + page source on failure.
        // Guard against duplicate registration across tests in the same JVM.
        if (!SelenideLogger.hasListener("allure")) {
            SelenideLogger.addListener("allure", new AllureSelenide()
                    .screenshots(true)
                    .savePageSource(true));
        }

        // Open the base URL in a fresh browser instance
        open("/");

        // Instantiating the page objects 
        loginPage = new LoginPage();
        inventoryPage = new InventoryPage();
        cartPage = new CartPage();
        checkoutInfoPage = new CheckoutInfoPage();
        checkoutOverviewPage = new CheckoutOverviewPage();
        checkoutCompletePage = new CheckoutCompletePage();
    }

    /**
     * Runs after each test method.
     * Closes the browser and releases the WebDriver instance managed by Selenide.
     */
    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }


    /**
     * JUnit 5 {@link TestWatcher} that logs a one-line result marker to the
     * console after each test completes.
     *
     * <ul>
     *   <li>{@code [PASS]} — test passed</li>
     *   <li>{@code [FAIL]} — test failed, includes the failure message</li>
     *   <li>{@code [SKIP]} — test was disabled / skipped</li>
     * </ul>
     */
    public static class JulTestWatcher implements TestWatcher {

        private static final Logger log = Logger.getLogger(JulTestWatcher.class.getName());

        /**
         * Invoked when a test completes successfully.
         *
         * @param context the JUnit 5 extension context carrying test metadata
         */
        @Override
        public void testSuccessful(ExtensionContext context) {
            log.info("[PASS] " + context.getDisplayName());
        }

        /**
         * Invoked when a test fails.
         *
         * @param context the JUnit 5 extension context carrying test metadata
         * @param cause   the throwable that caused the failure
         */
        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            log.info("[FAIL] " + context.getDisplayName() + " - " + cause.getMessage());
        }

        /**
         * Invoked when a test is disabled (skipped).
         *
         * @param context the JUnit 5 extension context carrying test metadata
         * @param reason  the optional reason the test was disabled
         */
        @Override
        public void testDisabled(ExtensionContext context, java.util.Optional<String> reason) {
            log.info("[SKIP] " + context.getDisplayName());
        }
    }

    /**
     * JUnit 5 extension that captures a screenshot at the moment a test throws an exception.
     * This runs before {@code @AfterEach}, so the browser is still open.
     */
    public static class ScreenshotOnFailureExtension implements TestExecutionExceptionHandler {

        @Override
        public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
            // Capture screenshot immediately when test fails, before @AfterEach closes browser
            try {
                captureScreenshot();
            } catch (Exception e) {
                // Screenshot capture failed — log and continue with test failure
                System.err.println("Failed to capture screenshot: " + e.getMessage());
            }
            // Re-throw the original exception so the test still fails
            throw throwable;
        }

        @Attachment(value = "Screenshot on failure", type = "image/png")
        private byte[] captureScreenshot() {
            try {
                // Use WebDriver's TakesScreenshot interface directly
                org.openqa.selenium.WebDriver driver = WebDriverRunner.getWebDriver();
                if (driver instanceof org.openqa.selenium.TakesScreenshot) {
                    return ((org.openqa.selenium.TakesScreenshot) driver)
                            .getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
                }
                System.err.println("WebDriver does not support screenshots");
            } catch (Exception e) {
                System.err.println("Screenshot capture exception: " + e.getMessage());
                e.printStackTrace();
            }
            return null; 
        }
    }
}
