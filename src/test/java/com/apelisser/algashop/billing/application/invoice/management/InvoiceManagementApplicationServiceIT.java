package com.apelisser.algashop.billing.application.invoice.management;

import com.apelisser.algashop.billing.domail.model.creditcard.CreditCard;
import com.apelisser.algashop.billing.domail.model.creditcard.CreditCardRepository;
import com.apelisser.algashop.billing.domail.model.creditcard.CreditCardTestDataBuilder;
import com.apelisser.algashop.billing.domail.model.invoice.Invoice;
import com.apelisser.algashop.billing.domail.model.invoice.InvoiceRepository;
import com.apelisser.algashop.billing.domail.model.invoice.InvoiceStatus;
import com.apelisser.algashop.billing.domail.model.invoice.InvoiceTestDataBuilder;
import com.apelisser.algashop.billing.domail.model.invoice.InvoicingService;
import com.apelisser.algashop.billing.domail.model.invoice.PaymentMethod;
import com.apelisser.algashop.billing.domail.model.invoice.payment.Payment;
import com.apelisser.algashop.billing.domail.model.invoice.payment.PaymentGatewayService;
import com.apelisser.algashop.billing.domail.model.invoice.payment.PaymentRequest;
import com.apelisser.algashop.billing.domail.model.invoice.payment.PaymentStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
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

    @MockitoBean
    PaymentGatewayService paymentGatewayService;

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

    @Test
    void shouldProcessInvoicePayment() {
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().build();
        invoice.changePaymentSettings(PaymentMethod.GATEWAY_BALANCE, null);

        invoiceRepository.saveAndFlush(invoice);

        Payment payment = Payment.builder()
            .gatewayCode("12345")
            .invoiceId(invoice.getId())
            .method(invoice.getPaymentSettings().getMethod())
            .status(PaymentStatus.PAID)
            .build();

        Mockito.when(paymentGatewayService.capture(Mockito.any(PaymentRequest.class))).thenReturn(payment);

        applicationService.processPayment(invoice.getId());

        Invoice paidInvoice = invoiceRepository.findById(invoice.getId()).orElseThrow();

        Assertions.assertThat(paidInvoice.isPaid()).isTrue();
        Mockito.verify(paymentGatewayService).capture(Mockito.any(PaymentRequest.class));
        Mockito.verify(invoicingService).assignPayment(Mockito.any(Invoice.class), Mockito.any(Payment.class));
    }

}