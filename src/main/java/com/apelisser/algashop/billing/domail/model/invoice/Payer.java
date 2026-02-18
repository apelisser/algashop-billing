package com.apelisser.algashop.billing.domail.model.invoice;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Payer {

    private String fullName;
    private String document;
    private String phone;
    private String email;
    private Address address;

    protected Payer() {
    }

    public Payer(String fullName, String document, String phone, String email, Address address) {
        this.fullName = fullName;
        this.document = document;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

}
