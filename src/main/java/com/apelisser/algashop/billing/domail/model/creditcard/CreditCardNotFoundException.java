package com.apelisser.algashop.billing.domail.model.creditcard;

import com.apelisser.algashop.billing.domail.model.DomainEntityNotFoundException;
import com.apelisser.algashop.billing.domail.model.ErrorMessages;

import java.io.Serial;
import java.util.UUID;

public class CreditCardNotFoundException extends DomainEntityNotFoundException {

    @Serial
    private static final long serialVersionUID = 3810610473669424729L;

    public CreditCardNotFoundException() {
        super(ErrorMessages.ERROR_CREDIT_CARD_NOT_FOUND);
    }

    public CreditCardNotFoundException(UUID creditCardId) {
        super(String.format(ErrorMessages.ERROR_CREDIT_CARD_BY_ID_NOT_FOUND, creditCardId));
    }

}
