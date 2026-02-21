package com.apelisser.algashop.billing.application.invoice.management;

import com.apelisser.algashop.billing.domail.model.creditcard.CreditCard;
import com.apelisser.algashop.billing.domail.model.creditcard.CreditCardRepository;
import com.apelisser.algashop.billing.domail.model.creditcard.CreditCardTestDataBuilder;
import com.apelisser.algashop.billing.domail.model.invoice.Invoice;
import com.apelisser.algashop.billing.domail.model.invoice.InvoiceRepository;
import com.apelisser.algashop.billing.domail.model.invoice.InvoiceStatus;
import com.apelisser.algashop.billing.domail.model.invoice.InvoicingService;
import com.apelisser.algashop.billing.domail.model.invoice.PaymentMethod;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
class InvoiceManagementApplicationServiceIT {

    @Autowired
    InvoiceManagementApplicationService applicationService;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    @MockitoSpyBean
    InvoicingService invoicingService;

    @Test
    void shouldGenerateInvoiceWithCreditCardAsPayment() {
        CreditCard creditCard = CreditCardTestDataBuilder.aCreditCard().build();
        creditCardRepository.saveAndFlush(creditCard);

        GenerateInvoiceInput input = GenerateInvoiceInputTestDataBuilder.anInput().build();

        input.setPaymentSettings(PaymentSettingsInput.builder()
            .creditCardId(creditCard.getId())
            .method(PaymentMethod.CREDIT_CARD)
            .build());

        UUID invoiceId = applicationService.generate(input);

        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow();

        Assertions.assertThat(invoice.getStatus()).isEqualTo(InvoiceStatus.UNPAID);
        Assertions.assertThat(invoice.getOrderId()).isEqualTo(input.getOrderId());

        Mockito.verify(invoicingService).issue(Mockito.any(), Mockito.any(), Mockito.any(),Mockito.any());
    }

    @Test
    void shouldGenerateInvoiceWithGatewayBalanceAsPayment() {
        CreditCard creditCard = CreditCardTestDataBuilder.aCreditCard().build();
        creditCardRepository.saveAndFlush(creditCard);

        GenerateInvoiceInput input = GenerateInvoiceInputTestDataBuilder.anInput().build();

        input.setPaymentSettings(PaymentSettingsInput.builder()
            .method(PaymentMethod.GATEWAY_BALANCE)
            .build());

        UUID invoiceId = applicationService.generate(input);

        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow();

        Assertions.assertThat(invoice.getStatus()).isEqualTo(InvoiceStatus.UNPAID);
        Assertions.assertThat(invoice.getOrderId()).isEqualTo(input.getOrderId());

        Mockito.verify(invoicingService).issue(Mockito.any(), Mockito.any(), Mockito.any(),Mockito.any());
    }

}