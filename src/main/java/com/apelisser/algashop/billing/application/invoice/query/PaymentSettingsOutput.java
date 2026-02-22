package com.apelisser.algashop.billing.application.invoice.query;

import com.apelisser.algashop.billing.domail.model.invoice.PaymentMethod;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PaymentSettingsOutput {

    private UUID id;
    private UUID creditCardId;
    private PaymentMethod method;

    public PaymentSettingsOutput() {
    }

    public PaymentSettingsOutput(UUID id, UUID creditCardId, PaymentMethod method) {
        this.id = id;
        this.creditCardId = creditCardId;
        this.method = method;
    }

}
