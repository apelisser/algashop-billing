package com.apelisser.algashop.billing.domail.model.invoice;

import com.apelisser.algashop.billing.domail.model.DomainException;
import com.apelisser.algashop.billing.domail.model.IdGenerator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
@Entity
public class Invoice {

    @Id
    private UUID id;
    private String orderId;
    private UUID customerId;
    private OffsetDateTime issuedAt;
    private OffsetDateTime paidAt;
    private OffsetDateTime cancelledAt;
    private OffsetDateTime expiredAt;
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
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
        Objects.requireNonNull(customerId);
        Objects.requireNonNull(payer);
        Objects.requireNonNull(items);

        if (StringUtils.isBlank(orderId)) {
            throw new IllegalArgumentException("Order ID cannot be blank");
        }

        if (items.isEmpty()) {
            throw new IllegalArgumentException("Items cannot be empty");
        }

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

    public boolean isCanceled() {
        return InvoiceStatus.CANCELED.equals(this.getStatus());
    }

    public boolean isUnpaid() {
        return InvoiceStatus.UNPAID.equals(this.getStatus());
    }

    public boolean isPaid() {
        return InvoiceStatus.PAID.equals(this.getStatus());
    }

    public void markAsPaid() {
        if (!isUnpaid()) {
            throw new DomainException(String.format("Invoice %s with status %s cannot be marked as paid",
                this.getId(), this.getStatus().toString().toLowerCase()));
        }

        setPaidAt(OffsetDateTime.now());
        setStatus(InvoiceStatus.PAID);
    }

    public void cancel(String cancelReason) {
        if (isCanceled()) {
            throw new DomainException(String.format("Invoice %s is already canceled", this.getId()));
        }

        setCancelReason(cancelReason);
        setCancelledAt(OffsetDateTime.now());
        setStatus(InvoiceStatus.CANCELED);
    }

    public void assignPaymentGatewayCode(String code) {
        if (!isUnpaid()) {
            throw new DomainException(String.format("Invoice %s with status %s cannot be edited",
                this.getId(), this.getStatus().toString().toLowerCase()));
        }

        if (this.getPaymentSettings() == null) {
            throw new DomainException(String.format("Invoice %s has no payment settings", this.getId()));
        }

        this.getPaymentSettings().assignGatewayCode(code);
    }

    public void changePaymentSettings(PaymentMethod method, UUID creditCardId) {
        if (!isUnpaid()) {
            throw new DomainException(String.format("Invoice %s with status %s cannot be edited",
                this.getId(), this.getStatus().toString().toLowerCase()));
        }

        PaymentSettings paymentSettings = PaymentSettings.brandNew(method, creditCardId);
        paymentSettings.setInvoice(this);
        this.setPaymentSettings(paymentSettings);
    }

}
