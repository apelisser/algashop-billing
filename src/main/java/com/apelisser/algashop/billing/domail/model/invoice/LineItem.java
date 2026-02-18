package com.apelisser.algashop.billing.domail.model.invoice;

import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode
public class LineItem {

    private Integer number;
    private String name;
    private BigDecimal amount;

}
