package com.automation.selenide.tests;

import com.automation.selenide.base.SetUp;
import com.automation.selenide.data.LoginData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Smoke tests for the SauceDemo login page.
 * <p>
 * Covers successful login for the standard user and negative scenarios
 * where login should fail and an error message should be displayed.
 * Tests start directly from the login page — no {@code @BeforeEach} login step.
 * </p>
 */
@Tag("smoke")
@DisplayName("Login Tests")
public class LoginTest extends SetUp {

    /**
     * Verifies that a standard user can log in successfully
     * and is redirected to the inventory page.
     */
    @Test
    @DisplayName("Standard user can login successfully")
    public void standardUserCanLogin() {
        loginPage.login(LoginData.STANDARD_USER, LoginData.PASSWORD);
        assertEquals(LoginData.INVENTORY_PAGE_TITLE, inventoryPage.getPageTitle());
    }

    /**
     * Verifies that invalid or restricted login attempts display the correct error message.
     *
     * @param username        the username to attempt login with
     * @param password        the password to attempt login with
     * @param expectedError   the error message expected to be shown
     */
    @ParameterizedTest(name = "{displayName} [{index}] — {2}")
    @MethodSource("invalidLoginScenarios")
    @DisplayName("Invalid login shows error message")
    public void invalidLoginShowsError(String username, String password, String expectedError) {
        loginPage.login(username, password);
        assertTrue(loginPage.isErrorDisplayed());
        assertEquals(expectedError, loginPage.getErrorMessage());
    }

    /**
     * Provides the four negative login scenarios:
     * <ol>
     *   <li>Locked-out user with valid password</li>
     *   <li>Invalid username with invalid password</li>
     *   <li>Empty username with valid password</li>
     *   <li>Valid username with empty password</li>
     * </ol>
     *
     * @return stream of arguments for {@link #invalidLoginShowsError}
     */
    static Stream<Arguments> invalidLoginScenarios() {
        return Stream.of(
            Arguments.of(LoginData.LOCKED_OUT_USER, LoginData.PASSWORD, LoginData.ERROR_LOCKED_OUT),
            Arguments.of(LoginData.INVALID_USERNAME, LoginData.INVALID_PASSWORD, LoginData.ERROR_INVALID_CREDENTIALS),
            Arguments.of("", LoginData.PASSWORD, LoginData.ERROR_USERNAME_REQUIRED),
            Arguments.of(LoginData.STANDARD_USER, "", LoginData.ERROR_PASSWORD_REQUIRED)
        );
    }
}
