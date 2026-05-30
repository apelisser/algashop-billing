package com.apelisser.algashop.billing.application.creditcard.management;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TokenizedCreditCardInput {

    @NotNull
    private UUID customerId;

    @NotBlank
    private String tokenizedCard;

}
