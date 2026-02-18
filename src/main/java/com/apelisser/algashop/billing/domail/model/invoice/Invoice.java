package com.apelisser.algashop.billing.domail.model.invoice;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
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

    protected Invoice() {
    }

    public Set<LineItem> getItems() {
        return Collections.unmodifiableSet(items);
    }

    public void markAsPaid() {
        // TODO
    }

    public void cancel() {
        // TODO
    }

    public void assingPaymentGatewayCode(String code) {
        // TODO
    }

    public void changePaymentSettings(PaymentMethod method, UUID creditCard) {
        // TODO
    }

}
