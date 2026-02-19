package com.apelisser.algashop.billing.domail.model.creditcard;

import com.apelisser.algashop.billing.domail.model.IdGenerator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class CreditCard {

    private UUID id;
    private OffsetDateTime createdAt;
    private UUID customerId;
    private String lastNumbers;
    private String brand;
    private Integer expMonth;
    private Integer expYear;

    @Setter(AccessLevel.PUBLIC)
    private String gatewayCode;

    protected CreditCard() {
    }

    protected CreditCard(UUID id, OffsetDateTime createdAt, UUID customerId, String lastNumbers, String brand,
            Integer expMonth, Integer expYear, String gatewayCode) {
        this.id = id;
        this.createdAt = createdAt;
        this.customerId = customerId;
        this.lastNumbers = lastNumbers;
        this.brand = brand;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.gatewayCode = gatewayCode;
    }

    public static CreditCard brandNew(UUID customerId, String lastNumbers, String brand, Integer expMonth,
            Integer expYear, String gatewayCreditCardCode) {
        return new CreditCard(
            IdGenerator.generateTimeBasedUUID(),
            OffsetDateTime.now(),
            customerId,
            lastNumbers,
            brand,
            expMonth,
            expYear,
            gatewayCreditCardCode
        );
    }

}
