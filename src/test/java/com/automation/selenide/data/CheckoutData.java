package com.automation.selenide.data;

/**
 * Test data constants for the checkout flow tests.
 * <p>
 * Covers customer info inputs, expected page titles at each checkout step,
 * the order confirmation message, and form validation error strings.
 * </p>
 */
public class CheckoutData {
    /** First name used when filling the checkout information form. */
    public static final String FIRST_NAME = "John";

    /** Last name used when filling the checkout information form. */
    public static final String LAST_NAME = "Doe";

    /** Postal code used when filling the checkout information form. */
    public static final String POSTAL_CODE = "12345";


    // Expected page titles (checkout flow steps)
    /** Page title displayed on the cart page. */
    public static final String CART_PAGE_TITLE = "Your Cart";

    /** Page title displayed on the checkout information step. */
    public static final String INFO_PAGE_TITLE = "Checkout:Your Information";

    /** Page title displayed on the checkout overview step. */
    public static final String OVERVIEW_PAGE_TITLE = "Checkout:Overview";

    /** Page title displayed on the checkout complete step. */
    public static final String COMPLETE_PAGE_TITLE = "Checkout: Complete!";


    // Order confirmation
    /** Header text displayed after an order is successfully placed. */
    public static final String CONFIRMATION_HEADER = "Thank you for your order!";


    // Form validation error messages
    /** Error shown when the first name field is left empty on submit. */
    public static final String ERROR_FIRST_NAME_REQUIRED = "Error: First Name is required";

    /** Error shown when the last name field is left empty on submit. */
    public static final String ERROR_LAST_NAME_REQUIRED = "Error: Last Name is required";

    /** Error shown when the postal code field is left empty on submit. */
    public static final String ERROR_POSTAL_CODE_REQUIRED = "Error: Postal Code is required";
}
