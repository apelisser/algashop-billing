package com.apelisser.algashop.billing.infrastructure.creditcard.fastpay;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FastpayCreditCardResponse {

    private String id;
    private String lastNumbers;
    private Integer expMonth;
    private Integer expYear;
    private String brand;

}
