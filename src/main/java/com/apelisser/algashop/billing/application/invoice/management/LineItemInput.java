package com.apelisser.algashop.billing.application.invoice.management;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private BigDecimal amount;

    public LineItemInput() {
    }

    public LineItemInput(String name, BigDecimal amount) {
        this.name = name;
        this.amount = amount;
    }

}
