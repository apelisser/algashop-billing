package com.apelisser.algashop.billing.infrastructure.payment.fastpay;

import com.apelisser.algashop.billing.domail.model.creditcard.CreditCard;
import com.apelisser.algashop.billing.domail.model.creditcard.CreditCardRepository;
import com.apelisser.algashop.billing.domail.model.creditcard.LimitedCreditCard;
import com.apelisser.algashop.billing.domail.model.invoice.InvoiceTestDataBuilder;
import com.apelisser.algashop.billing.domail.model.invoice.PaymentMethod;
import com.apelisser.algashop.billing.domail.model.invoice.payment.Payment;
import com.apelisser.algashop.billing.domail.model.invoice.payment.PaymentRequest;
import com.apelisser.algashop.billing.infrastructure.AbstractFastpayIT;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
@Transactional
class PaymentGatewayServiceFastpayImplIT extends AbstractFastpayIT {

    @Autowired
    PaymentGatewayServiceFastpayImpl paymentGatewayServiceFastpay;

    @Autowired
    CreditCardRepository creditCardRepository;

    @BeforeAll
    public static void setUpAll() {
        startWireMock();
    }

    @AfterAll
    public static void tearDownAll() {
        stopWireMock();
    }

    @Test
    void shouldProcessPaymentWithCreditCard() {
        LimitedCreditCard limitedCreditCard = registerCard();

        CreditCard creditCard = CreditCard.brandNew(
            validCustomerId,
            limitedCreditCard.getLastNumbers(),
            limitedCreditCard.getBrand(),
            limitedCreditCard.getExpMonth(),
            limitedCreditCard.getExpYear(),
            limitedCreditCard.getGatewayCode());

        creditCardRepository.save(creditCard);

        UUID invoiceId = UUID.randomUUID();
        PaymentRequest request = PaymentRequest.builder()
            .method(PaymentMethod.CREDIT_CARD)
            .amount(new BigDecimal("1000.00"))
            .invoiceId(invoiceId)
            .creditCardId(creditCard.getId())
            .payer(InvoiceTestDataBuilder.aPayer())
            .build();

        Payment payment = paymentGatewayServiceFastpay.capture(request);

        Assertions.assertThat(payment.getInvoiceId()).isEqualTo(invoiceId);
        System.out.println(payment.getGatewayCode());
    }

}