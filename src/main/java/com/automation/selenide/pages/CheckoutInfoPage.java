package com.automation.selenide.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

/** Page object for the checkout information step ({@code /checkout-step-one.html}). */
public class CheckoutInfoPage {

    private final SelenideElement pageTitle      = $(".title");
    private final SelenideElement firstNameField = $("#first-name");
    private final SelenideElement lastNameField  = $("#last-name");
    private final SelenideElement postalCodeField = $("#postal-code");
    private final SelenideElement continueButton = $("[data-test='continue']");
    private final SelenideElement cancelButton   = $("[data-test='cancel']");
    private final SelenideElement errorMessage   = $("[data-test='error']");

    public String getPageTitle() {
        return pageTitle.getText();
    }

    public void enterCustomerInfo(String firstName, String lastName, String postalCode) {
        firstNameField.setValue(firstName);
        lastNameField.setValue(lastName);
        postalCodeField.setValue(postalCode);
        continueButton.click();
    }

    public void submitWithoutInfo() {
        continueButton.click();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public boolean isErrorDisplayed() {
        return errorMessage.isDisplayed();
    }

    public void cancelCheckout() {
        cancelButton.click();
    }
}
