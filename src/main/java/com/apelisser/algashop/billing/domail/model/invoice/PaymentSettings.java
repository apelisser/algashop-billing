package com.apelisser.algashop.billing.domail.model.invoice;

import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(of = "id")
public class PaymentSettings {

    private UUID id;
    private UUID creditCardId;
    private String gatewayCode;
    private PaymentMethod method;

}
