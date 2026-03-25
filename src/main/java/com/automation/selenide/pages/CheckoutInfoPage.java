package com.automation.selenide.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

/** Page object for the checkout information step ({@code /checkout-step-one.html}). */
public class CheckoutInfoPage {
    private final SelenideElement pageTitle = $(".title");
    private final SelenideElement firstNameField = $("#first-name");
    private final SelenideElement lastNameField = $("#last-name");
    private final SelenideElement postalCodeField = $("#postal-code");
    private final SelenideElement continueButton = $("[data-test='continue']");
    private final SelenideElement cancelButton = $("[data-test='cancel']");
    private final SelenideElement errorMessage = $("[data-test='error']");

    /**
     * Returns the visible page title text.
     *
     * @return the page title string
     */
    public String getPageTitle() {
        return pageTitle.getText();
    }

    /**
     * Fills in the customer information form and clicks Continue.
     *
     * @param firstName  the customer's first name
     * @param lastName   the customer's last name
     * @param postalCode the customer's postal / zip code
     */
    public void enterCustomerInfo(String firstName, String lastName, String postalCode) {
        firstNameField.setValue(firstName);
        lastNameField.setValue(lastName);
        postalCodeField.setValue(postalCode);
        continueButton.click();
    }

    /**
     * Clicks the Continue button without filling in any form fields,
     * triggering validation error messages.
     */
    public void submitWithoutInfo() {
        continueButton.click();
    }

    /**
     * Returns the validation error message text.
     *
     * @return error message string
     */
    public String getErrorMessage() {
        return errorMessage.getText();
    }

    /**
     * Checks whether the validation error message is currently displayed.
     *
     * @return {@code true} if the error element is visible
     */
    public boolean isErrorDisplayed() {
        return errorMessage.isDisplayed();
    }

    /**
     * Clicks the Cancel button to abort checkout and return to the cart.
     */
    public void cancelCheckout() {
        cancelButton.click();
    }
}
