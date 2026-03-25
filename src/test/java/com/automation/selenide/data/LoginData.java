package com.automation.selenide.data;

/**
 * Test data constants for login-related tests.
 * <p>
 * Covers valid credentials, invalid credentials, and expected error messages
 * used across {@code LoginTest} and any test that requires authentication.
 * </p>
 */
public class LoginData {
    /** Password shared by all valid SauceDemo accounts. */
    public static final String PASSWORD = "secret_sauce";


    // Valid usernames
    /** Standard user — full access, no quirks. Used as the default test account. */
    public static final String STANDARD_USER = "standard_user";

    /** Problem user — images and interactions are intentionally broken. */
    public static final String PROBLEM_USER = "problem_user";

    /** Performance glitch user — simulates slow page loads. */
    public static final String PERFORMANCE_USER = "performance_glitch_user";

    /** Error user — certain actions trigger server-side errors. */
    public static final String ERROR_USER = "error_user";

    /** Visual user — UI has intentional visual defects. */
    public static final String VISUAL_USER = "visual_user";


    // Invalid / restricted usernames
    /** Locked-out user — valid credentials but access is denied by the app. */
    public static final String LOCKED_OUT_USER = "locked_out_user";

    /** Username that does not exist in the system. */
    public static final String INVALID_USERNAME = "invalid_user";

    /** Password that does not match any account. */
    public static final String INVALID_PASSWORD = "wrong_password";


    // Expected page title after successful login
    /** Page title displayed on the inventory page after a successful login. */
    public static final String INVENTORY_PAGE_TITLE = "Products";


    // Expected error messages
    /** Error shown when a locked-out user attempts to log in. */
    public static final String ERROR_LOCKED_OUT = "Epic sadface: Sorry, this user has been locked out.";

    /** Error shown when username/password combination is not recognised. */
    public static final String ERROR_INVALID_CREDENTIALS = "Epic sadface: Username and password do not match any user in this service";

    /** Error shown when the username field is left empty on submit. */
    public static final String ERROR_USERNAME_REQUIRED = "Epic sadface: Username is required";

    /** Error shown when the password field is left empty on submit. */
    public static final String ERROR_PASSWORD_REQUIRED = "Epic sadface: Password is required";
}
