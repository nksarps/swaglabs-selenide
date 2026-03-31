package com.automation.selenide.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/** Page object for the SauceDemo cart page ({@code /cart.html}). */
public class CartPage {
    private final SelenideElement pageTitle = $(".title");
    private final ElementsCollection cartItems = $$(".cart_item");
    private final SelenideElement checkoutButton = $("[data-test='checkout']");
    private final SelenideElement continueShoppingButton = $("[data-test='continue-shopping']");

    /**
     * Returns the visible page title text.
     *
     * @return the page title string
     */
    public String getPageTitle() {
        return pageTitle.getText();
    }

    /**
     * Returns the number of items currently in the cart.
     *
     * @return cart item count
     */
    public int getCartItemCount() {
        return cartItems.size();
    }

    /**
     * Clicks the remove button for a specific product.
     *
     * @param removeButtonDataTest the {@code data-test} attribute value of the remove button
     */
    public void removeProduct(String removeButtonDataTest) {
        $("[data-test='" + removeButtonDataTest + "']").click();
    }

    /**
     * Clicks the Checkout button to proceed to the checkout flow.
     */
    public void proceedToCheckout() {
        checkoutButton.click();
    }

    /**
     * Clicks the Continue Shopping button to return to the inventory page.
     */
    public void continueShopping() {
        continueShoppingButton.click();
    }
}
