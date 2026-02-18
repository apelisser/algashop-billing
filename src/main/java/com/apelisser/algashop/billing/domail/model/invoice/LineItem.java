package com.apelisser.algashop.billing.domail.model.invoice;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode
public class LineItem {

    private Integer number;
    private String name;
    private BigDecimal amount;

    protected LineItem() {
    }

    public LineItem(Integer number, String name, BigDecimal amount) {
        this.number = number;
        this.name = name;
        this.amount = amount;
    }

}
