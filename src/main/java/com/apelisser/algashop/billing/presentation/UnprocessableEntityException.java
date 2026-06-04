package com.apelisser.algashop.billing.presentation;

import java.io.Serial;

public class UnprocessableEntityException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -9200547343953771926L;

    public UnprocessableEntityException() {
    }

    public UnprocessableEntityException(String message) {
        super(message);
    }

    public UnprocessableEntityException(String message, Throwable cause) {
        super(message, cause);
    }

}
