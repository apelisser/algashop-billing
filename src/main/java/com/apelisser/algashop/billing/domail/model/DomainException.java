package com.apelisser.algashop.billing.domail.model;

import java.io.Serial;

public class DomainException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3801043806351938155L;

    public DomainException(String message) {
        super(message);
    }

}
