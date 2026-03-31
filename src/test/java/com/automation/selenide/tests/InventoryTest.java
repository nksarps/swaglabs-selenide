package com.automation.selenide.tests;

import com.automation.selenide.base.SetUp;
import com.automation.selenide.data.LoginData;
import com.automation.selenide.data.ProductData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Smoke tests for the SauceDemo inventory (products) page.
 * <p>
 * Each test logs in as the standard user before execution via {@link #login()}.
 * Covers page title, product count, cart badge behaviour, and logout.
 * </p>
 */
@Tag("smoke")
@DisplayName("Inventory Tests")
public class InventoryTest extends SetUp {
    /**
     * Logs in as the standard user before each test so all inventory
     * tests start from the products page.
     */
    @BeforeEach
    public void login() {
        loginPage.login(LoginData.STANDARD_USER, LoginData.PASSWORD);
    }

    /**
     * Verifies the inventory page displays the correct title after login.
     */
    @Test
    @DisplayName("Inventory page has correct title")
    public void inventoryPageHasCorrectTitle() {
        assertEquals(ProductData.PAGE_TITLE, inventoryPage.getPageTitle());
    }

    /**
     * Verifies that all six products are displayed on the inventory page.
     */
    @Test
    @DisplayName("Inventory page displays all products")
    public void inventoryPageDisplaysAllProducts() {
        assertEquals(ProductData.TOTAL_PRODUCT_COUNT, inventoryPage.getProductCount());
    }

    /**
     * Verifies that adding one product to the cart shows the cart badge
     * with a count of 1.
     */
    @Test
    @DisplayName("Adding a product updates the cart badge")
    public void addingProductUpdatesCartBadge() {
        inventoryPage.addProductToCart(ProductData.ADD_BACKPACK);
        assertTrue(inventoryPage.isCartBadgeDisplayed());
        assertEquals("1", inventoryPage.getCartBadgeCount());
    }

    /**
     * Verifies that adding two products to the cart updates the badge count to 2.
     */
    @Test
    @DisplayName("Adding multiple products updates badge count")
    public void addingMultipleProductsUpdatesBadgeCount() {
        inventoryPage.addProductToCart(ProductData.ADD_BACKPACK);
        inventoryPage.addProductToCart(ProductData.ADD_BIKE_LIGHT);
        assertEquals("2", inventoryPage.getCartBadgeCount());
    }

    /**
     * Verifies that the user can log out via the burger menu and is
     * returned to the login page (error element absent, login form accessible).
     */
    @Test
    @DisplayName("User can logout successfully")
    public void userCanLogout() {
        inventoryPage.logout();
        // After logout the login page is shown — error should not be displayed
        assertFalse(loginPage.isErrorDisplayed());
    }

    /**
     * Verifies that the cart badge is not displayed before any product is added.
     * Covers the initial empty-cart state on page load.
     */
    @Test
    @DisplayName("Cart badge not displayed initially")
    public void cartBadgeNotDisplayedInitially() {
        assertFalse(inventoryPage.isCartBadgeDisplayed());
    }

    /**
     * Verifies that adding a product flips the button state from
     * "Add to cart" to "Remove" for that product.
     */
    @Test
    @DisplayName("Adding a product shows the remove button")
    public void addingProductShowsRemoveButton() {
        inventoryPage.addProductToCart(ProductData.ADD_BACKPACK);
        assertTrue(inventoryPage.isRemoveButtonDisplayed(ProductData.REMOVE_BACKPACK));
    }
}
