package com.apelisser.algashop.billing.domail.model.invoice;

import com.apelisser.algashop.billing.domail.model.DomainException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

class InvoiceTest {

    @Test
    void shouldIssueInvoiceWhenValidItemsAreProvided() {
        String orderId = "01226N0693HDA";
        UUID customerId = UUID.randomUUID();
        Payer payer = InvoiceTestDataBuilder.aPayer();
        Set<LineItem> items = Set.of(InvoiceTestDataBuilder.aLineItem(), InvoiceTestDataBuilder.aLineItemAlt());

        BigDecimal expectedTotalAmount = items.stream()
            .map(LineItem::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        Invoice invoice = Invoice.issue(orderId, customerId, payer, items);

        Assertions.assertThat(invoice).isNotNull();
        Assertions.assertThat(invoice.getId()).isNotNull();
        Assertions.assertThat(invoice.getPayer()).isNotNull();
        Assertions.assertThat(invoice.isUnpaid()).isTrue();
        Assertions.assertThat(invoice.getItems()).hasSize(items.size());
        Assertions.assertThat(invoice.getTotalAmount()).isEqualTo(expectedTotalAmount);
    }

    @Test
    void shouldThrowExceptionWhenChangingInvoiceItems() {
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().build();

        Assertions.assertThatExceptionOfType(UnsupportedOperationException.class)
            .isThrownBy(() -> invoice.getItems().clear());
    }

    @Test
    void shouldMarkInvoiceAsPaidWhenInvoiceIsUnpaid() {
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().status(InvoiceStatus.UNPAID).build();

        invoice.markAsPaid();

        Assertions.assertThat(invoice.isPaid()).isTrue();
        Assertions.assertThat(invoice.getPaidAt()).isNotNull();
    }

    @Test
    void shouldCancelInvoiceWhenInvoiceIsNotCanceled() {
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().status(InvoiceStatus.UNPAID).build();

        invoice.cancel("Customer requested");

        Assertions.assertThat(invoice.isCanceled()).isTrue();
        Assertions.assertThat(invoice.getCancelledAt()).isNotNull();
        Assertions.assertThat(invoice.getCancelReason()).isEqualTo("Customer requested");
    }

    @Test
    void shouldChangePaymentSettingsWhenInvoiceIsUnpaid() {
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().status(InvoiceStatus.UNPAID).build();

        UUID creditCardId = UUID.randomUUID();
        PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD;
        invoice.changePaymentSettings(paymentMethod, creditCardId);

        Assertions.assertThat(invoice.getPaymentSettings()).isNotNull();
        Assertions.assertThat(invoice.getPaymentSettings().getCreditCardId()).isEqualTo(creditCardId);
        Assertions.assertThat(invoice.getPaymentSettings().getMethod()).isEqualTo(paymentMethod);
    }

    @Test
    void shouldAssignPaymentGatewayCodeWhenInvoiceIsUnpaid() {
        Invoice invoice = InvoiceTestDataBuilder.anInvoice()
            .status(InvoiceStatus.UNPAID)
            .paymentSettings(PaymentMethod.CREDIT_CARD, UUID.randomUUID())
            .build();

        String gatewayCode = "123456";
        invoice.assignPaymentGatewayCode(gatewayCode);

        Assertions.assertThat(invoice.getPaymentSettings()).isNotNull();
        Assertions.assertThat(invoice.getPaymentSettings().getGatewayCode())
            .isNotNull()
            .isEqualTo(gatewayCode);
    }

    @Test
    void shouldThrowExceptionWhenIssuingInvoiceWithNoItems() {
        ThrowableAssert.ThrowingCallable callableInvoiceWithNoItems = () -> InvoiceTestDataBuilder.anInvoice()
            .items(Collections.emptySet())
            .build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(callableInvoiceWithNoItems);
    }

    @Test
    void shouldThrowExceptionWhenMarkingCanceledInvoiceAsPaid() {
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().status(InvoiceStatus.CANCELED).build();

        Assertions.assertThatExceptionOfType(DomainException.class)
            .isThrownBy(invoice::markAsPaid);
    }

    @Test
    void shouldThrowExceptionWhenCancelingAlreadyCanceledInvoice() {
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().status(InvoiceStatus.CANCELED).build();

        Assertions.assertThatExceptionOfType(DomainException.class)
            .isThrownBy(() -> invoice.cancel("Customer requested"));
    }

    @Test
    void givenPaidInvoice_whenChangePaymentSettings_shouldThrowIDomainException() {
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().status(InvoiceStatus.PAID).build();

        Assertions.assertThatExceptionOfType(DomainException.class)
            .isThrownBy(() -> invoice.changePaymentSettings(PaymentMethod.GATEWAY_BALANCE, null));
    }

    @Test
    void givenPaidInvoice_whenAssignGatewayCode_shouldThrowIDomainException() {
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().status(InvoiceStatus.PAID).build();

        Assertions.assertThatExceptionOfType(DomainException.class)
            .isThrownBy(() -> invoice.assignPaymentGatewayCode("123456"));
    }

}