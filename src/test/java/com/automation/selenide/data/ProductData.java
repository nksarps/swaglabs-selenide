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

    /** {@code data-test} value for the "Add to cart" button of the Sauce Labs Bolt T-Shirt. */
    public static final String ADD_BOLT_TSHIRT = "add-to-cart-sauce-labs-bolt-t-shirt";

    /** {@code data-test} value for the "Add to cart" button of the Sauce Labs Fleece Jacket. */
    public static final String ADD_FLEECE_JACKET = "add-to-cart-sauce-labs-fleece-jacket";

    /** {@code data-test} value for the "Add to cart" button of the Sauce Labs Onesie. */
    public static final String ADD_ONESIE = "add-to-cart-sauce-labs-onesie";

    /** {@code data-test} value for the "Add to cart" button of the Test.allTheThings() T-Shirt (Red). */
    public static final String ADD_RED_TSHIRT = "add-to-cart-test.allthethings()-t-shirt-(red)";

    // Remove button data-test attribute values
    /** {@code data-test} value for the "Remove" button of the Sauce Labs Backpack. */
    public static final String REMOVE_BACKPACK = "remove-sauce-labs-backpack";

    /** {@code data-test} value for the "Remove" button of the Sauce Labs Bike Light. */
    public static final String REMOVE_BIKE_LIGHT = "remove-sauce-labs-bike-light";

    /** {@code data-test} value for the "Remove" button of the Sauce Labs Bolt T-Shirt. */
    public static final String REMOVE_BOLT_TSHIRT = "remove-sauce-labs-bolt-t-shirt";

    /** {@code data-test} value for the "Remove" button of the Sauce Labs Fleece Jacket. */
    public static final String REMOVE_FLEECE_JACKET = "remove-sauce-labs-fleece-jacket";

    /** {@code data-test} value for the "Remove" button of the Sauce Labs Onesie. */
    public static final String REMOVE_ONESIE = "remove-sauce-labs-onesie";

    /** {@code data-test} value for the "Remove" button of the Test.allTheThings() T-Shirt (Red). */
    public static final String REMOVE_RED_TSHIRT = "remove-test.allthethings()-t-shirt-(red)";


    // Inventory page metadata
    /** Total number of products listed on the inventory page. */
    public static final int TOTAL_PRODUCT_COUNT = 6;

    /** Expected page title displayed on the inventory page. */
    public static final String PAGE_TITLE = "Products";
}
