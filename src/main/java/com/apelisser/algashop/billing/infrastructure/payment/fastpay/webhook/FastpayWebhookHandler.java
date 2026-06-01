package com.apelisser.algashop.billing.infrastructure.payment.fastpay.webhook;

import com.apelisser.algashop.billing.application.invoice.management.InvoiceManagementApplicationService;
import com.apelisser.algashop.billing.infrastructure.payment.fastpay.FastpayEnumConverter;
import com.apelisser.algashop.billing.infrastructure.payment.fastpay.FastpayPaymentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FastpayWebhookHandler {

    private static final Logger log = LoggerFactory.getLogger(FastpayWebhookHandler.class);

    private final InvoiceManagementApplicationService invoiceManagementApplicationService;

    public FastpayWebhookHandler(InvoiceManagementApplicationService invoiceManagementApplicationService) {
        this.invoiceManagementApplicationService = invoiceManagementApplicationService;
    }

    public void process(FastpayPaymentWebhookEvent event) {
        log.info("Processing webhook event: {}", event);
        invoiceManagementApplicationService.updatePaymentStatus(
            UUID.fromString(event.getReferenceCode()),
            FastpayEnumConverter.convert(FastpayPaymentStatus.valueOf(event.getStatus()))
        );

    }

}
