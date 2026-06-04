package com.apelisser.algashop.billing.domail.model;

public final class ErrorMessages {

    public static final String ERROR_CREDIT_CARD_NOT_FOUND = "Credit card not found";
    public static final String ERROR_INVOICE_NOT_FOUND = "Invoice not found";
    public static final String ERROR_INVOICE_BY_ID_NOT_FOUND = "Invoice %s not found";
    public static final String ERROR_INVOICE_BY_ORDER_NOT_FOUND = "No invoice found for orderId %s";

    private ErrorMessages() {
        throw new UnsupportedOperationException("Utility class");
    }

}
