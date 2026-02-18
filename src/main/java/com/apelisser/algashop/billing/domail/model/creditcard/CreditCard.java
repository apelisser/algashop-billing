package com.apelisser.algashop.billing.domail.model.creditcard;

import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.UUID;

@EqualsAndHashCode(of = "id")
public class CreditCard {

    private UUID id;
    private OffsetDateTime createdAt;
    private UUID customerId;
    private String lastNumbers;
    private String brand;
    private Integer expMonth;
    private Integer expYear;
    private String gatewayCode;

}
