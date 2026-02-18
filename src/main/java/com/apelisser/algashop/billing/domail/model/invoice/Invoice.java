package com.apelisser.algashop.billing.domail.model.invoice;

import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(of = "id")
public class Invoice {

    private UUID id;
    private String orderId;
    private UUID customerId;
    private OffsetDateTime issuedAt;
    private OffsetDateTime paidAt;
    private OffsetDateTime cancelledAt;
    private OffsetDateTime expiredAt;
    private BigDecimal totalAmount;
    private InvoiceStatus status;
    private PaymentSettings paymentSettings;
    private Set<LineItem> items = new HashSet<>();
    private Payer payer;
    private String cancelReason;


}
