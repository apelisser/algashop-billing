package com.apelisser.algashop.billing.presentation;

import java.io.Serial;

public class GatewayTimeoutException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6146023454361243531L;

    public GatewayTimeoutException() {
    }

    public GatewayTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

}
