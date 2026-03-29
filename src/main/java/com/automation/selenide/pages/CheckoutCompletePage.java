package com.automation.selenide.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

/** Page object for the checkout complete step ({@code /checkout-complete.html}). */
public class CheckoutCompletePage {
    private final SelenideElement pageTitle = $(".title");
    private final SelenideElement confirmationHeader = $(".complete-header");
    private final SelenideElement backToProductsButton = $("[data-test='back-to-products']");

    /**
     * Returns the visible page title text.
     *
     * @return the page title string
     */
    public String getPageTitle() {
        return pageTitle.getText();
    }

    /**
     * Returns the order confirmation header text (e.g. "Thank you for your order!").
     *
     * @return confirmation header string
     */
    public String getConfirmationHeader() {
        return confirmationHeader.getText();
    }

    /**
     * Checks whether the order confirmation header is displayed on the page.
     *
     * @return {@code true} if the confirmation header is visible
     */
    public boolean isOrderConfirmed() {
        return confirmationHeader.isDisplayed();
    }

    /**
     * Clicks the Back to Products button to return to the inventory page.
     */
    public void backToProducts() {
        backToProductsButton.click();
    }
}
