package com.apelisser.algashop.billing.domail.model.invoice;

import com.apelisser.algashop.billing.domail.model.IdGenerator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class PaymentSettings {

    private UUID id;
    private UUID creditCardId;
    private String gatewayCode;
    private PaymentMethod method;

    protected PaymentSettings() {
    }

    protected PaymentSettings(UUID id, UUID creditCardId, String gatewayCode, PaymentMethod method) {
        this.id = id;
        this.creditCardId = creditCardId;
        this.gatewayCode = gatewayCode;
        this.method = method;
    }

    public static PaymentSettings brandNew(PaymentMethod method, UUID creditCardId) {
        return new PaymentSettings(
            IdGenerator.generateTimeBasedUUID(),
            creditCardId,
            null,
            method
        );
    }

    void assignGatewayCode(String gatewayCode) {
        this.gatewayCode = gatewayCode;
    }

}
