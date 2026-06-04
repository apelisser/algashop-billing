package com.apelisser.algashop.billing.domail.model;

import java.io.Serial;

public class DomainEntityNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8154276271915427825L;

    public DomainEntityNotFoundException() {
    }

    public DomainEntityNotFoundException(String message) {
        super(message);
    }

}
