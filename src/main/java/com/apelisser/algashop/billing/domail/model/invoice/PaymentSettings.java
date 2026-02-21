package com.apelisser.algashop.billing.domail.model.invoice;

import com.apelisser.algashop.billing.domail.model.DomainException;
import com.apelisser.algashop.billing.domail.model.IdGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
@Entity
public class PaymentSettings {

    @Id
    private UUID id;
    private UUID creditCardId;
    private String gatewayCode;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PACKAGE)
    @OneToOne(mappedBy = "paymentSettings")
    private Invoice invoice;

    protected PaymentSettings() {
    }

    protected PaymentSettings(UUID id, UUID creditCardId, String gatewayCode, PaymentMethod method, Invoice invoice) {
        this.id = id;
        this.creditCardId = creditCardId;
        this.gatewayCode = gatewayCode;
        this.method = method;
    }

    static PaymentSettings brandNew(PaymentMethod method, UUID creditCardId) {
        Objects.requireNonNull(method);
        if (method.equals(PaymentMethod.CREDIT_CARD)) {
            Objects.requireNonNull(creditCardId);
        }

        return new PaymentSettings(
            IdGenerator.generateTimeBasedUUID(),
            creditCardId,
            null,
            method,
            null
        );
    }

    void assignGatewayCode(String gatewayCode) {
        if (StringUtils.isBlank(gatewayCode)) {
            throw new IllegalArgumentException("Gateway code cannot be blank");
        }

        if (this.getGatewayCode() != null) {
            throw new DomainException("Gateway code already assigned");
        }

        this.gatewayCode = gatewayCode;
    }

}
