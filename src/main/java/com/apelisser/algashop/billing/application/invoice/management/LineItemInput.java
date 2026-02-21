package com.apelisser.algashop.billing.application.invoice.management;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class LineItemInput {

    private String name;
    private BigDecimal amount;

    public LineItemInput() {
    }

    public LineItemInput(String name, BigDecimal amount) {
        this.name = name;
        this.amount = amount;
    }

}
