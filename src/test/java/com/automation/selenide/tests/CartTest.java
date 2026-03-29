package com.automation.selenide.tests;

import com.automation.selenide.base.SetUp;
import com.automation.selenide.data.CheckoutData;
import com.automation.selenide.data.LoginData;
import com.automation.selenide.data.ProductData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Regression tests for the SauceDemo cart page.
 * <p>
 * Each test starts with the standard user logged in, the backpack added to
 * the cart, and the cart page open — set up via {@link #loginAndAddProduct(TestInfo)}.
 * </p>
 */
@Tag("regression")
@DisplayName("Cart Tests")
public class CartTest extends SetUp {
    /**
     * Logs in as the standard user, adds the backpack to the cart,
     * and navigates to the cart page before each test.
     *
     * @param testInfo JUnit 5 injected test metadata
     */
    @BeforeEach
    public void loginAndAddProduct(TestInfo testInfo) {
        loginPage.login(LoginData.STANDARD_USER, LoginData.PASSWORD);
        inventoryPage.addProductToCart(ProductData.ADD_BACKPACK);
        inventoryPage.goToCart();
    }

    /**
     * Verifies the cart page displays the correct title.
     */
    @Test
    @DisplayName("Cart page has correct title")
    public void cartPageHasCorrectTitle() {
        assertEquals(CheckoutData.CART_PAGE_TITLE, cartPage.getPageTitle());
    }

    /**
     * Verifies that the backpack added in setup appears as a single item in the cart.
     */
    @Test
    @DisplayName("Cart contains the added product")
    public void cartContainsAddedProduct() {
        assertEquals(1, cartPage.getCartItemCount());
    }

    /**
     * Verifies that removing the backpack from the cart leaves it empty.
     */
    @Test
    @DisplayName("Removing a product empties the cart")
    public void removingProductEmptiesCart() {
        cartPage.removeProduct(ProductData.REMOVE_BACKPACK);
        assertEquals(0, cartPage.getCartItemCount());
    }

    /**
     * Verifies that clicking Checkout navigates to the checkout information page.
     */
    @Test
    @DisplayName("Proceed to checkout navigates to info page")
    public void proceedToCheckoutNavigatesToInfoPage() {
        cartPage.proceedToCheckout();
        assertEquals(CheckoutData.INFO_PAGE_TITLE, checkoutInfoPage.getPageTitle());
    }

    /**
     * Verifies that clicking Continue Shopping from the cart returns the user
     * to the inventory page with the correct title.
     */
    @Test
    @DisplayName("Continue shopping returns to inventory page")
    public void continueShoppingReturnsToInventory() {
        cartPage.continueShopping();
        assertEquals(ProductData.PAGE_TITLE, inventoryPage.getPageTitle());
    }
}
