package com.automation.selenide.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/** Page object for the SauceDemo inventory page ({@code /inventory.html}). */
public class InventoryPage {

    private final SelenideElement    pageTitle       = $(".title");
    private final ElementsCollection inventoryItems  = $$(".inventory_item");
    private final SelenideElement    cartIcon        = $(".shopping_cart_link");
    private final SelenideElement    cartBadge       = $(".shopping_cart_badge");
    private final SelenideElement    burgerMenuButton = $("#react-burger-menu-btn");
    private final SelenideElement    logoutLink      = $("#logout_sidebar_link");

    public String getPageTitle() {
        return pageTitle.getText();
    }

    public ElementsCollection getAllProducts() {
        return inventoryItems;
    }

    public int getProductCount() {
        return inventoryItems.size();
    }

    public void addProductToCart(String addButtonDataTest) {
        $("[data-test='" + addButtonDataTest + "']").click();
    }

    public String getCartBadgeCount() {
        return cartBadge.getText();
    }

    public boolean isCartBadgeDisplayed() {
        return cartBadge.isDisplayed();
    }

    public void goToCart() {
        cartIcon.click();
    }

    public void logout() {
        burgerMenuButton.click();
        logoutLink.click();
    }
}
