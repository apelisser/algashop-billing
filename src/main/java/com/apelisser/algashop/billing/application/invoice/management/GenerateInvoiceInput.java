package com.apelisser.algashop.billing.application.invoice.management;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class GenerateInvoiceInput {

    private String orderId;
    private UUID customerId;
    private PaymentSettingsInput paymentSettings;
    private PayerData payer;
    private List<LineItemInput> items;

    public GenerateInvoiceInput() {
    }

    public GenerateInvoiceInput(String orderId, UUID customerId, PaymentSettingsInput paymentSettings,
            PayerData payer, List<LineItemInput> items) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.paymentSettings = paymentSettings;
        this.payer = payer;
        this.items = items;
    }

}
