package com.apelisser.algashop.billing.domail.model.invoice;

import com.apelisser.algashop.billing.domail.model.FieldValidations;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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

    @Builder
    public Payer(String fullName, String document, String phone, String email, Address address) {
        FieldValidations.requiresNonBlank(fullName);
        FieldValidations.requiresNonBlank(document);
        FieldValidations.requiresNonBlank(phone);
        FieldValidations.requiresValidEmail(email);
        Objects.requireNonNull(address);

        this.fullName = fullName;
        this.document = document;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

}
