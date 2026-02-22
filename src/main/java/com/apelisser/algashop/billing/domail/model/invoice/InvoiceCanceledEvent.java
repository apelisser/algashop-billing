package com.apelisser.algashop.billing.domail.model.invoice;

import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
public class InvoiceCanceledEvent {

    private final UUID invoiceId;
    private final UUID customerId;
    private final String orderId;
    private final OffsetDateTime canceledAt;

    public InvoiceCanceledEvent(UUID invoiceId, UUID customerId, String orderId, OffsetDateTime canceledAt) {
        this.invoiceId = invoiceId;
        this.customerId = customerId;
        this.orderId = orderId;
        this.canceledAt = canceledAt;
    }

}
