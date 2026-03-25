package com.automation.selenide.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/** Page object for the checkout overview step ({@code /checkout-step-two.html}). */
public class CheckoutOverviewPage {

    private final SelenideElement    pageTitle      = $(".title");
    private final ElementsCollection orderItems     = $$(".cart_item");
    private final SelenideElement    subtotalLabel  = $(".summary_subtotal_label");
    private final SelenideElement    taxLabel       = $(".summary_tax_label");
    private final SelenideElement    totalLabel     = $(".summary_total_label");
    private final SelenideElement    finishButton   = $("[data-test='finish']");
    private final SelenideElement    cancelButton   = $("[data-test='cancel']");

    public String getPageTitle() {
        return pageTitle.getText();
    }

    public ElementsCollection getOrderItems() {
        return orderItems;
    }

    public String getSubtotal() {
        return subtotalLabel.getText();
    }

    public String getTax() {
        return taxLabel.getText();
    }

    public String getTotal() {
        return totalLabel.getText();
    }

    public void finishOrder() {
        finishButton.click();
    }

    public void cancelOrder() {
        cancelButton.click();
    }
}
