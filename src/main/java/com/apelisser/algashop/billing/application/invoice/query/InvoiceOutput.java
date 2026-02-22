package com.apelisser.algashop.billing.application.invoice.query;

import com.apelisser.algashop.billing.application.invoice.management.PayerData;
import com.apelisser.algashop.billing.domail.model.invoice.InvoiceStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class InvoiceOutput {

    private UUID id;
    private String orderId;
    private UUID customerId;
    private OffsetDateTime issuedAt;
    private OffsetDateTime paidAt;
    private OffsetDateTime canceledAt;
    private OffsetDateTime expiresAt;
    private BigDecimal totalAmount;
    private InvoiceStatus status;
    private PayerData payer;
    private PaymentSettingsOutput paymentSettings;

    public InvoiceOutput() {
    }

    public InvoiceOutput(UUID id, String orderId, UUID customerId, OffsetDateTime issuedAt, OffsetDateTime paidAt,
            OffsetDateTime canceledAt, OffsetDateTime expiresAt, BigDecimal totalAmount, InvoiceStatus status,
            PayerData payer, PaymentSettingsOutput paymentSettings) {
        this.id = id;
        this.orderId = orderId;
        this.customerId = customerId;
        this.issuedAt = issuedAt;
        this.paidAt = paidAt;
        this.canceledAt = canceledAt;
        this.expiresAt = expiresAt;
        this.totalAmount = totalAmount;
        this.status = status;
        this.payer = payer;
        this.paymentSettings = paymentSettings;
    }

}
