package com.apelisser.algashop.billing.infrastructure.payment.fake;

import com.apelisser.algashop.billing.domail.model.invoice.PaymentMethod;
import com.apelisser.algashop.billing.domail.model.invoice.payment.Payment;
import com.apelisser.algashop.billing.domail.model.invoice.payment.PaymentGatewayService;
import com.apelisser.algashop.billing.domail.model.invoice.payment.PaymentRequest;
import com.apelisser.algashop.billing.domail.model.invoice.payment.PaymentStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@ConditionalOnProperty(name = "algashop.integrations.payment.provider", havingValue = "FAKE")
public class PaymentGatewayServiceFakeImpl implements PaymentGatewayService {

    @Override
    public Payment capture(PaymentRequest request) {
        return Payment.builder()
            .invoiceId(request.getInvoiceId())
            .status(PaymentStatus.PAID)
            .method(request.getMethod())
            .gatewayCode(UUID.randomUUID().toString())
            .build();
    }

    @Override
    public Payment findByCode(String gatewayCode) {
        return Payment.builder()
            .invoiceId(UUID.randomUUID())
            .status(PaymentStatus.PAID)
            .method(PaymentMethod.GATEWAY_BALANCE)
            .gatewayCode(gatewayCode)
            .build();
    }

}
