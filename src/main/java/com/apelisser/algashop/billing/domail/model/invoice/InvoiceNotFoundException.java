package com.apelisser.algashop.billing.domail.model.invoice;

import com.apelisser.algashop.billing.domail.model.DomainEntityNotFoundException;
import com.apelisser.algashop.billing.domail.model.ErrorMessages;

import java.io.Serial;
import java.util.UUID;

public class InvoiceNotFoundException extends DomainEntityNotFoundException {

    @Serial
    private static final long serialVersionUID = 4114638360573851842L;

    public InvoiceNotFoundException() {
        super(ErrorMessages.ERROR_INVOICE_NOT_FOUND);
    }

    public InvoiceNotFoundException(UUID invoiceId) {
        super(String.format(ErrorMessages.ERROR_INVOICE_BY_ID_NOT_FOUND, invoiceId));
    }

}
