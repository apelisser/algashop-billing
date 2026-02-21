package com.apelisser.algashop.billing.application.invoice.management;

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

    private String fullName;
    private String document;
    private String email;
    private String phone;
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
