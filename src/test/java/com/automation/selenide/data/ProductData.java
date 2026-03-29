package com.automation.selenide.data;

/**
 * Test data constants for inventory and cart product interactions.
 * <p>
 * Contains the {@code data-test} attribute values used to locate add-to-cart
 * and remove buttons dynamically, plus general inventory page metadata.
 * </p>
 */
public class ProductData {
    // Add-to-cart button data-test attribute values
    /** {@code data-test} value for the "Add to cart" button of the Sauce Labs Backpack. */
    public static final String ADD_BACKPACK = "add-to-cart-sauce-labs-backpack";

    /** {@code data-test} value for the "Add to cart" button of the Sauce Labs Bike Light. */
    public static final String ADD_BIKE_LIGHT = "add-to-cart-sauce-labs-bike-light";

    // Remove button data-test attribute values
    /** {@code data-test} value for the "Remove" button of the Sauce Labs Backpack. */
    public static final String REMOVE_BACKPACK = "remove-sauce-labs-backpack";


    // Inventory page metadata
    /** Total number of products listed on the inventory page. */
    public static final int TOTAL_PRODUCT_COUNT = 6;

    /** Expected page title displayed on the inventory page. */
    public static final String PAGE_TITLE = "Products";
}
