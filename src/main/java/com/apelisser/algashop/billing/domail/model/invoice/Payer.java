package com.apelisser.algashop.billing.domail.model.invoice;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Payer {

    private String fullName;
    private String document;
    private String phone;
    private String email;
    private Address address;

}
