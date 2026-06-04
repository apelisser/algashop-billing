package com.apelisser.algashop.billing.application.invoice.management;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class PayerData {

    @NotBlank
    private String fullName;

    @NotBlank
    private String document;

    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    @Valid
    @NotNull
    private AddressData address;

    public PayerData() {
    }

    public PayerData(String fullName, String document, String email, String phone, AddressData address) {
        this.fullName = fullName;
        this.document = document;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

}
