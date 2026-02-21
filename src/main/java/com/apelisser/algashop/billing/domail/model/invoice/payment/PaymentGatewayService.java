package com.apelisser.algashop.billing.domail.model.invoice.payment;

public interface PaymentGatewayService {

    Payment capture(PaymentRequest request);

    Payment findByCode(String gatewayCode);

}
