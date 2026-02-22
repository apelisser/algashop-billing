package com.apelisser.algashop.billing.domail.model.invoice;

import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
public class InvoiceIssuedEvent {

    private final UUID invoiceId;
    private final UUID customerId;
    private final String orderId;
    private final OffsetDateTime issuedAt;

    public InvoiceIssuedEvent(UUID invoiceId, UUID customerId, String orderId, OffsetDateTime issuedAt) {
        this.invoiceId = invoiceId;
        this.customerId = customerId;
        this.orderId = orderId;
        this.issuedAt = issuedAt;
    }

}
