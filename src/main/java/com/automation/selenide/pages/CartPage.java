package com.automation.selenide.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/** Page object for the SauceDemo cart page ({@code /cart.html}). */
public class CartPage {

    private final SelenideElement    pageTitle              = $(".title");
    private final ElementsCollection cartItems              = $$(".cart_item");
    private final SelenideElement    checkoutButton         = $("[data-test='checkout']");
    private final SelenideElement    continueShoppingButton = $("[data-test='continue-shopping']");

    public String getPageTitle() {
        return pageTitle.getText();
    }

    public ElementsCollection getCartItems() {
        return cartItems;
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    public void removeProduct(String removeButtonDataTest) {
        $("[data-test='" + removeButtonDataTest + "']").click();
    }

    public void proceedToCheckout() {
        checkoutButton.click();
    }

    public void continueShopping() {
        continueShoppingButton.click();
    }
}
