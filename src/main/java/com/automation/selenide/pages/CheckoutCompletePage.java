package com.automation.selenide.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

/** Page object for the checkout complete step ({@code /checkout-complete.html}). */
public class CheckoutCompletePage {

    private final SelenideElement pageTitle             = $(".title");
    private final SelenideElement confirmationHeader    = $(".complete-header");
    private final SelenideElement confirmationText      = $(".complete-text");
    private final SelenideElement backToProductsButton  = $("[data-test='back-to-products']");

    public String getPageTitle() {
        return pageTitle.getText();
    }

    public String getConfirmationHeader() {
        return confirmationHeader.getText();
    }

    public String getConfirmationText() {
        return confirmationText.getText();
    }

    public boolean isOrderConfirmed() {
        return confirmationHeader.isDisplayed();
    }

    public void backToProducts() {
        backToProductsButton.click();
    }
}
