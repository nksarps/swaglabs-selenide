package com.automation.selenide.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/** Page object for the checkout overview step ({@code /checkout-step-two.html}). */
public class CheckoutOverviewPage {
    private final SelenideElement pageTitle = $(".title");
    private final ElementsCollection orderItems = $$(".cart_item");
    private final SelenideElement subtotalLabel = $(".summary_subtotal_label");
    private final SelenideElement taxLabel = $(".summary_tax_label");
    private final SelenideElement totalLabel = $(".summary_total_label");
    private final SelenideElement finishButton = $("[data-test='finish']");
    private final SelenideElement cancelButton = $("[data-test='cancel']");

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
     * Returns the subtotal label text (e.g. "Item total: $29.99").
     *
     * @return subtotal label string
     */
    public String getSubtotal() {
        return subtotalLabel.getText();
    }

    /**
     * Returns the tax label text (e.g. "Tax: $2.40").
     *
     * @return tax label string
     */
    public String getTax() {
        return taxLabel.getText();
    }

    /**
     * Returns the total label text (e.g. "Total: $32.39").
     *
     * @return total label string
     */
    public String getTotal() {
        return totalLabel.getText();
    }

    /**
     * Clicks the Finish button to complete the order.
     */
    public void finishOrder() {
        finishButton.click();
    }

    /**
     * Clicks the Cancel button to abort the order and return to the inventory page.
     */
    public void cancelOrder() {
        cancelButton.click();
    }
}
