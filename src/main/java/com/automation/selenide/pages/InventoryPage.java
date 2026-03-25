package com.automation.selenide.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/** Page object for the SauceDemo inventory page ({@code /inventory.html}). */
public class InventoryPage {

    private final SelenideElement    pageTitle        = $(".title");
    private final ElementsCollection inventoryItems   = $$(".inventory_item");
    private final SelenideElement    cartIcon         = $(".shopping_cart_link");
    private final SelenideElement    cartBadge        = $(".shopping_cart_badge");
    private final SelenideElement    burgerMenuButton = $("#react-burger-menu-btn");
    private final SelenideElement    logoutLink       = $("#logout_sidebar_link");

    /**
     * Returns the visible page title text.
     *
     * @return the page title string
     */
    public String getPageTitle() {
        return pageTitle.getText();
    }

    /**
     * Returns all product item elements on the inventory page.
     *
     * @return collection of inventory item elements
     */
    public ElementsCollection getAllProducts() {
        return inventoryItems;
    }

    /**
     * Returns the total number of products displayed on the page.
     *
     * @return product count
     */
    public int getProductCount() {
        return inventoryItems.size();
    }

    /**
     * Clicks the Add to Cart button for a specific product.
     *
     * @param addButtonDataTest the {@code data-test} attribute value of the add-to-cart button
     */
    public void addProductToCart(String addButtonDataTest) {
        $("[data-test='" + addButtonDataTest + "']").click();
    }

    /**
     * Checks whether the remove button for a specific product is currently displayed.
     * Used to verify that the button state flipped from "Add to cart" to "Remove"
     * after a product has been added.
     *
     * @param removeButtonDataTest the {@code data-test} attribute value of the remove button
     * @return {@code true} if the remove button is visible
     */
    public boolean isRemoveButtonDisplayed(String removeButtonDataTest) {
        return $("[data-test='" + removeButtonDataTest + "']").isDisplayed();
    }

    /**
     * Returns the cart badge count text showing how many items are in the cart.
     *
     * @return cart badge count string
     */
    public String getCartBadgeCount() {
        return cartBadge.getText();
    }

    /**
     * Checks whether the cart badge is currently displayed.
     *
     * @return {@code true} if the cart badge is visible
     */
    public boolean isCartBadgeDisplayed() {
        return cartBadge.isDisplayed();
    }

    /**
     * Clicks the shopping cart icon to navigate to the cart page.
     */
    public void goToCart() {
        cartIcon.click();
    }

    /**
     * Opens the burger menu and clicks the Logout link to sign out.
     */
    public void logout() {
        burgerMenuButton.click();
        logoutLink.click();
    }
}
