package com.apelisser.algashop.billing.domail.model.invoice.payment;

import com.apelisser.algashop.billing.domail.model.FieldValidations;
import com.apelisser.algashop.billing.domail.model.invoice.PaymentMethod;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@Builder
public class Payment {

    private String gatewayCode;
    private UUID invoiceId;
    private PaymentMethod method;
    private PaymentStatus status;

    public Payment(String gatewayCode, UUID invoiceId, PaymentMethod method, PaymentStatus status) {
        FieldValidations.requiresNonBlank(gatewayCode);
        Objects.requireNonNull(invoiceId);
        Objects.requireNonNull(method);
        Objects.requireNonNull(status);

        this.gatewayCode = gatewayCode;
        this.invoiceId = invoiceId;
        this.method = method;
        this.status = status;
    }

}
