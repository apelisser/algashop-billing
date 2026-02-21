package com.apelisser.algashop.billing.domail.model.creditcard;

import com.apelisser.algashop.billing.domail.model.IdGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
@Entity
public class CreditCard {

    @Id
    private UUID id;
    private OffsetDateTime createdAt;
    private UUID customerId;
    private String lastNumbers;
    private String brand;
    private Integer expMonth;
    private Integer expYear;
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
        Objects.requireNonNull(customerId);
        Objects.requireNonNull(expMonth);
        Objects.requireNonNull(expYear);

        if (StringUtils.isBlank(lastNumbers)) {
            throw new IllegalArgumentException("Last numbers cannot be blank");
        }

        if (StringUtils.isBlank(brand)) {
            throw new IllegalArgumentException("Brand cannot be blank");
        }

        if (StringUtils.isBlank(gatewayCreditCardCode)) {
            throw new IllegalArgumentException("Gateway credit card code cannot be blank");
        }

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

    public void setGatewayCode(String gatewayCode) {
        if (StringUtils.isBlank(gatewayCode)) {
            throw new IllegalArgumentException("Gateway credit card code cannot be blank");
        }

        this.gatewayCode = gatewayCode;
    }

}
