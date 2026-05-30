package com.apelisser.algashop.billing.infrastructure.payment.fastpay;

import com.apelisser.algashop.billing.domail.model.invoice.PaymentMethod;
import com.apelisser.algashop.billing.domail.model.invoice.payment.PaymentStatus;

public final class FastpayEnumConverter {

    private FastpayEnumConverter() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static PaymentMethod convert(FastpayPaymentMethod method) {
        return switch (method) {
            case CREDIT -> PaymentMethod.CREDIT_CARD;
            case GATEWAY_BALANCE -> PaymentMethod.GATEWAY_BALANCE;
        };
    }

    public static PaymentStatus convert(FastpayPaymentStatus status) {
        return switch (status) {
            case PENDING -> PaymentStatus.PENDING;
            case PROCESSING -> PaymentStatus.PROCESSING;
            case FAILED, CANCELED -> PaymentStatus.FAILED;
            case PAID -> PaymentStatus.PAID;
            case REFUNDED -> PaymentStatus.REFUNDED;
        };
    }

}
