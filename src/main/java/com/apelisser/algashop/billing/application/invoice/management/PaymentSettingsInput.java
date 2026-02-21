package com.apelisser.algashop.billing.application.invoice.management;

import com.apelisser.algashop.billing.domail.model.invoice.PaymentMethod;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class PaymentSettingsInput {

    private PaymentMethod method;
    private UUID creditCardId;

    public PaymentSettingsInput() {
    }

    public PaymentSettingsInput(PaymentMethod method, UUID creditCardId) {
        this.method = method;
        this.creditCardId = creditCardId;
    }

}
