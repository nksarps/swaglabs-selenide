package com.automation.selenide.tests;

import com.automation.selenide.base.SetUp;
import com.automation.selenide.data.CheckoutData;
import com.automation.selenide.data.LoginData;
import com.automation.selenide.data.ProductData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Regression tests for the SauceDemo checkout flow.
 * <p>
 * Each test starts with the standard user logged in, the backpack added,
 * and the checkout information page open — set up via
 * {@link #loginAddProductAndProceedToCheckout()}.
 * </p>
 */
@Tag("regression")
@DisplayName("Checkout Tests")
public class CheckoutTest extends SetUp {

    /**
     * Logs in, adds the backpack, navigates to the cart, and proceeds to
     * the checkout information page before each test.
     */
    @BeforeEach
    public void loginAddProductAndProceedToCheckout() {
        loginPage.login(LoginData.STANDARD_USER, LoginData.PASSWORD);
        inventoryPage.addProductToCart(ProductData.ADD_BACKPACK);
        inventoryPage.goToCart();
        cartPage.proceedToCheckout();
    }

    /**
     * Verifies the checkout information page displays the correct title.
     */
    @Test
    @DisplayName("Checkout info page has correct title")
    public void checkoutInfoPageHasCorrectTitle() {
        assertEquals(CheckoutData.INFO_PAGE_TITLE, checkoutInfoPage.getPageTitle());
    }

    /**
     * Verifies that submitting the checkout form with missing fields shows
     * the appropriate validation error message.
     *
     * @param firstName first name to enter (empty string means leave blank)
     * @param lastName last name to enter (empty string means leave blank)
     * @param postalCode postal code to enter (empty string means leave blank)
     * @param expectedError the validation error message expected to appear
     */
    @ParameterizedTest(name = "{displayName} [{index}] — {3}")
    @MethodSource("checkoutFormValidationScenarios")
    @DisplayName("Incomplete form shows validation error")
    public void incompleteFormShowsValidationError(
            String firstName, String lastName, String postalCode, String expectedError) {
        if (firstName.isEmpty() && lastName.isEmpty() && postalCode.isEmpty()) {
            checkoutInfoPage.submitWithoutInfo();
        } else {
            // Fill only the provided fields then submit via enterCustomerInfo
            // We need partial submission — fill fields individually then click continue
            if (!firstName.isEmpty()) $setField("first-name", firstName);
            if (!lastName.isEmpty()) $setField("last-name",  lastName);
            if (!postalCode.isEmpty()) $setField("postal-code", postalCode);
            checkoutInfoPage.submitWithoutInfo();
        }
        assertTrue(checkoutInfoPage.isErrorDisplayed());
        assertEquals(expectedError, checkoutInfoPage.getErrorMessage());
    }

    /**
     * Verifies that filling in valid customer info navigates to the overview page.
     */
    @Test
    @DisplayName("Valid info navigates to checkout overview")
    public void validInfoNavigatesToOverview() {
        checkoutInfoPage.enterCustomerInfo(
                CheckoutData.FIRST_NAME, CheckoutData.LAST_NAME, CheckoutData.POSTAL_CODE);
        assertEquals(CheckoutData.OVERVIEW_PAGE_TITLE, checkoutOverviewPage.getPageTitle());
    }

    /**
     * Verifies that the checkout overview page lists exactly one ordered item.
     */
    @Test
    @DisplayName("Overview page shows the ordered item")
    public void overviewPageShowsOrderedItem() {
        checkoutInfoPage.enterCustomerInfo(
                CheckoutData.FIRST_NAME, CheckoutData.LAST_NAME, CheckoutData.POSTAL_CODE);
        assertEquals(1, checkoutOverviewPage.getOrderItems().size());
    }

    /**
     * Verifies the full happy-path order completion: correct page title,
     * confirmation header text, and {@code isOrderConfirmed()} returning true.
     */
    @Test
    @DisplayName("Finishing order navigates to confirmation page")
    public void finishingOrderNavigatesToConfirmation() {
        checkoutInfoPage.enterCustomerInfo(
                CheckoutData.FIRST_NAME, CheckoutData.LAST_NAME, CheckoutData.POSTAL_CODE);
        checkoutOverviewPage.finishOrder();
        assertEquals(CheckoutData.COMPLETE_PAGE_TITLE, checkoutCompletePage.getPageTitle());
        assertTrue(checkoutCompletePage.isOrderConfirmed());
        assertEquals(CheckoutData.CONFIRMATION_HEADER, checkoutCompletePage.getConfirmationHeader());
    }

    /**
     * Verifies that cancelling on the checkout info page returns the user
     * to the cart page with the correct title.
     */
    @Test
    @DisplayName("Cancel checkout returns to cart page")
    public void cancelCheckoutReturnsToCart() {
        checkoutInfoPage.cancelCheckout();
        assertEquals(CheckoutData.CART_PAGE_TITLE, cartPage.getPageTitle());
    }

    /**
     * Verifies that clicking Back to Products on the confirmation page
     * returns the user to the inventory page with the correct title.
     */
    @Test
    @DisplayName("Back to products returns to inventory page")
    public void backToProductsReturnsToInventory() {
        checkoutInfoPage.enterCustomerInfo(
                CheckoutData.FIRST_NAME, CheckoutData.LAST_NAME, CheckoutData.POSTAL_CODE);
        checkoutOverviewPage.finishOrder();
        checkoutCompletePage.backToProducts();
        assertEquals(ProductData.PAGE_TITLE, inventoryPage.getPageTitle());
    }

    /**
     * Provides three form validation scenarios — each omits one required field:
     * <ol>
     *   <li>Missing first name</li>
     *   <li>Missing last name</li>
     *   <li>Missing postal code</li>
     * </ol>
     *
     * @return stream of arguments for {@link #incompleteFormShowsValidationError}
     */
    static Stream<Arguments> checkoutFormValidationScenarios() {
        return Stream.of(
            Arguments.of("", CheckoutData.LAST_NAME, CheckoutData.POSTAL_CODE, CheckoutData.ERROR_FIRST_NAME_REQUIRED),
            Arguments.of(CheckoutData.FIRST_NAME, "", CheckoutData.POSTAL_CODE, CheckoutData.ERROR_LAST_NAME_REQUIRED),
            Arguments.of(CheckoutData.FIRST_NAME, CheckoutData.LAST_NAME, "", CheckoutData.ERROR_POSTAL_CODE_REQUIRED)
        );
    }

    /**
     * Sets the value of a form field identified by its {@code id} attribute.
     * Used to partially fill the checkout form for validation testing.
     *
     * @param fieldId the HTML {@code id} of the input element
     * @param value the value to type into the field
     */
    private void $setField(String fieldId, String value) {
        com.codeborne.selenide.Selenide.$("#" + fieldId).setValue(value);
    }
}
