package com.apelisser.algashop.billing.infrastructure.payment.fastpay;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FastpayPaymentModel {

    private String id;
    private String referenceCode;
    private String status;
    private String method;
    private BigDecimal totalAmount;

}
