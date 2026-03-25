package com.automation.selenide.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

/** Page object for the SauceDemo login page ({@code /}). */
public class LoginPage {
    private final SelenideElement usernameField = $("#user-name");
    private final SelenideElement passwordField = $("#password");
    private final SelenideElement loginButton   = $("#login-button");
    private final SelenideElement errorMessage  = $("[data-test='error']");

    /**
     * Enters credentials into the login form and submits it.
     *
     * @param username the account username
     * @param password the account password
     */
    public void login(String username, String password) {
        usernameField.setValue(username);
        passwordField.setValue(password);
        loginButton.click();
    }

    /**
     * Attempts a login expected to fail (e.g. invalid credentials).
     * Delegates to {@link #login(String, String)}.
     *
     * @param username the account username
     * @param password the account password
     */
    public void loginExpectingFailure(String username, String password) {
        login(username, password);
    }

    /**
     * Returns the error message text shown after a failed login attempt.
     *
     * @return error message string
     */
    public String getErrorMessage() {
        return errorMessage.getText();
    }

    /**
     * Checks whether the login error message is currently displayed.
     *
     * @return {@code true} if the error element is visible
     */
    public boolean isErrorDisplayed() {
        return errorMessage.isDisplayed();
    }
}