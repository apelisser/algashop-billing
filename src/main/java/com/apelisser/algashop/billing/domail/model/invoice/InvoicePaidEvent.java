package com.apelisser.algashop.billing.domail.model.invoice;

import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
public class InvoicePaidEvent {

    private final UUID invoiceId;
    private final UUID customerId;
    private final String orderId;
    private final OffsetDateTime paidAt;

    public InvoicePaidEvent(UUID invoiceId, UUID customerId, String orderId, OffsetDateTime paidAt) {
        this.invoiceId = invoiceId;
        this.customerId = customerId;
        this.orderId = orderId;
        this.paidAt = paidAt;
    }

}
