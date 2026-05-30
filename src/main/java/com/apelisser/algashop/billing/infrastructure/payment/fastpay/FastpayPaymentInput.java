package com.apelisser.algashop.billing.infrastructure.payment.fastpay;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class FastpayPaymentInput {

    private String referenceCode;
    private BigDecimal totalAmount;
    private String method;
    private String CreditCard;
    private String fullName;
    private String document;
    private String phone;
    private String adressLine1;
    private String adressLine2;
    private String zipCode;
    private String replyToUrl;

}
