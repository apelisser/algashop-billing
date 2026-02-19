package com.apelisser.algashop.billing.domail.model.invoice;

import com.apelisser.algashop.billing.domail.model.IdGenerator;
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

    protected Invoice(UUID id, String orderId, UUID customerId, OffsetDateTime issuedAt, OffsetDateTime paidAt,
            OffsetDateTime cancelledAt, OffsetDateTime expiredAt, BigDecimal totalAmount, InvoiceStatus status,
            PaymentSettings paymentSettings, Set<LineItem> items, Payer payer, String cancelReason) {
        this.id = id;
        this.orderId = orderId;
        this.customerId = customerId;
        this.issuedAt = issuedAt;
        this.paidAt = paidAt;
        this.cancelledAt = cancelledAt;
        this.expiredAt = expiredAt;
        this.totalAmount = totalAmount;
        this.status = status;
        this.paymentSettings = paymentSettings;
        this.items = items;
        this.payer = payer;
        this.cancelReason = cancelReason;
    }

    public static Invoice issue(String orderId, UUID customerId, Payer payer, Set<LineItem> items) {
        BigDecimal totalAmount = items.stream()
            .map(LineItem::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new Invoice(
            IdGenerator.generateTimeBasedUUID(),
            orderId,
            customerId,
            OffsetDateTime.now(),
            null,
            null,
            OffsetDateTime.now().plusDays(3),
            totalAmount,
            InvoiceStatus.UNPAID,
            null,
            items,
            payer,
            null
        );
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

    public void changePaymentSettings(PaymentMethod method, UUID creditCardId) {
        PaymentSettings paymentSettings = PaymentSettings.brandNew(method, creditCardId);
        this.setPaymentSettings(paymentSettings);
    }

}
