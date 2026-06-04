package com.apelisser.algashop.billing.presentation;

import java.io.Serial;

public class BadGatewayException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7319239818862648381L;

    public BadGatewayException() {
    }

    public BadGatewayException(String message, Throwable cause) {
        super(message, cause);
    }

}
