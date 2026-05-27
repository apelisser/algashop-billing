package com.apelisser.algashop.billing.domail.model.creditcard;

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
public class LimitedCreditCard {

    private String gatewayCode;
    private String lastNumbers;
    private String brand;
    private Integer expMonth;
    private Integer expYear;

}
