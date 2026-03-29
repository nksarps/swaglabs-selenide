package com.automation.selenide.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/** Page object for the checkout overview step ({@code /checkout-step-two.html}). */
public class CheckoutOverviewPage {
    private final SelenideElement pageTitle = $(".title");
    private final ElementsCollection orderItems = $$(".cart_item");
    private final SelenideElement finishButton = $("[data-test='finish']");

    /**
     * Returns the visible page title text.
     *
     * @return the page title string
     */
    public String getPageTitle() {
        return pageTitle.getText();
    }

    /**
     * Returns all order item elements listed in the overview.
     *
     * @return collection of order item elements
     */
    public ElementsCollection getOrderItems() {
        return orderItems;
    }

    /**
     * Clicks the Finish button to complete the order.
     */
    public void finishOrder() {
        finishButton.click();
    }
}
