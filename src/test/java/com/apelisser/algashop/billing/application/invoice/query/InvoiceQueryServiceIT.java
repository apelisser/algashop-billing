package com.apelisser.algashop.billing.application.invoice.query;

import com.apelisser.algashop.billing.domail.model.invoice.Invoice;
import com.apelisser.algashop.billing.domail.model.invoice.InvoiceRepository;
import com.apelisser.algashop.billing.domail.model.invoice.InvoiceTestDataBuilder;
import com.apelisser.algashop.billing.domail.model.invoice.PaymentMethod;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class InvoiceQueryServiceIT {

    @Autowired
    InvoiceQueryService invoiceQueryService;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Test
    void shouldFindByOrderId() {
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().build();
        invoice.changePaymentSettings(PaymentMethod.GATEWAY_BALANCE, null);
        invoiceRepository.saveAndFlush(invoice);

        InvoiceOutput invoiceOutput = invoiceQueryService.findByOrderId(invoice.getOrderId());
        Assertions.assertThat(invoiceOutput.getId()).isEqualTo(invoice.getId());
    }

}