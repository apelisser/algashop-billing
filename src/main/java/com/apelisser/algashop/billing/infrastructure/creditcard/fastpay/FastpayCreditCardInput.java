package com.apelisser.algashop.billing.infrastructure.creditcard.fastpay;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class FastpayCreditCardInput {

    private String tokenizedCard;
    private String customerCode;

}
