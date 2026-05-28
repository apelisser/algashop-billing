package com.apelisser.algashop.billing.infrastructure.creditcard.fastpay;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FastpayTokenizationInput {

    private String number;
    private String cvv;
    private String holderName;
    private String holderDocument;
    private Integer expMonth;
    private Integer expYear;

    public FastpayTokenizationInput() {
    }

    public FastpayTokenizationInput(String number, String cvv, String holderName, String holderDocument,
        Integer expMonth, Integer expYear) {
        this.number = number;
        this.cvv = cvv;
        this.holderName = holderName;
        this.holderDocument = holderDocument;
        this.expMonth = expMonth;
        this.expYear = expYear;
    }

}
