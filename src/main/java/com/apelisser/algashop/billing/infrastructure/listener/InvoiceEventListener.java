package com.apelisser.algashop.billing.infrastructure.listener;

import com.apelisser.algashop.billing.domail.model.invoice.InvoiceCanceledEvent;
import com.apelisser.algashop.billing.domail.model.invoice.InvoiceIssuedEvent;
import com.apelisser.algashop.billing.domail.model.invoice.InvoicePaidEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InvoiceEventListener {

    private static final Logger log = LoggerFactory.getLogger(InvoiceEventListener.class);

    @EventListener
    public void listen(InvoiceIssuedEvent event) {
        log.info("Received InvoiceIssuedEvent");
    }

    @EventListener
    public void listen(InvoicePaidEvent event) {
        log.info("Received InvoicePaidEvent");
    }

    @EventListener
    public void listen(InvoiceCanceledEvent event) {
        log.info("Received InvoiceCanceledEvent");
    }

}
