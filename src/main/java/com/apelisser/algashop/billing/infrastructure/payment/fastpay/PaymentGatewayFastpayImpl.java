package com.apelisser.algashop.billing.infrastructure.payment.fastpay;

import com.apelisser.algashop.billing.domail.model.creditcard.CreditCard;
import com.apelisser.algashop.billing.domail.model.creditcard.CreditCardNotFoundException;
import com.apelisser.algashop.billing.domail.model.creditcard.CreditCardRepository;
import com.apelisser.algashop.billing.domail.model.invoice.Address;
import com.apelisser.algashop.billing.domail.model.invoice.Payer;
import com.apelisser.algashop.billing.domail.model.invoice.payment.Payment;
import com.apelisser.algashop.billing.domail.model.invoice.payment.PaymentGatewayService;
import com.apelisser.algashop.billing.domail.model.invoice.payment.PaymentRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@ConditionalOnProperty(name = "algashop.integrations.payment.provider", havingValue = "FASTPAY")
public class PaymentGatewayFastpayImpl implements PaymentGatewayService {

    private final FastpayPaymentAPIClient fastpayPaymentAPIClient;
    private final CreditCardRepository creditCardRepository;

    public PaymentGatewayFastpayImpl(FastpayPaymentAPIClient fastpayPaymentAPIClient,
            CreditCardRepository creditCardRepository) {
        this.fastpayPaymentAPIClient = fastpayPaymentAPIClient;
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public Payment capture(PaymentRequest request) {
        FastpayPaymentInput input = convertToInput(request);
        FastpayPaymentModel response = fastpayPaymentAPIClient.capture(input);
        return convertToPayment(response);
    }

    @Override
    public Payment findByCode(String gatewayCode) {
        FastpayPaymentModel response = fastpayPaymentAPIClient.findById(gatewayCode);
        return convertToPayment(response);
    }

    private FastpayPaymentInput convertToInput(PaymentRequest request) {
        Payer payer = request.getPayer();
        Address address = payer.getAddress();

        var builder =  FastpayPaymentInput.builder()
            .totalAmount(request.getAmount())
            .referenceCode(request.getInvoiceId().toString())
            .fullName(payer.getFullName())
            .document(payer.getDocument())
            .phone(payer.getPhone())
            .zipCode(address.getZipCode())
            .addressLine1(address.getStreet() + ", " + address.getNumber())
            .addressLine2(address.getComplement())
            .replyToUrl("http://example.com/webhook");

        switch (request.getMethod()) {
            case CREDIT_CARD -> {
                builder.method(FastpayPaymentMethod.CREDIT.name());
                CreditCard creditCard = creditCardRepository.findById(request.getCreditCardId())
                    .orElseThrow(CreditCardNotFoundException::new);
                builder.creditCardId(creditCard.getGatewayCode());
            }
            case GATEWAY_BALANCE -> builder.method(FastpayPaymentMethod.GATEWAY_BALANCE.name());
        }

        return builder.build();
    }

    private Payment convertToPayment(FastpayPaymentModel response) {
        var builder = Payment.builder()
            .gatewayCode(response.getId())
            .invoiceId(UUID.fromString(response.getReferenceCode()));

        FastpayPaymentMethod fastpayPaymentMethod;
        try {
            fastpayPaymentMethod = FastpayPaymentMethod.valueOf(response.getMethod());
        } catch (Exception e) {
            throw new IllegalArgumentException("Unknown payment method: " + response.getMethod());
        }

        FastpayPaymentStatus fastpayPaymentStatus;
        try{
            fastpayPaymentStatus = FastpayPaymentStatus.valueOf(response.getStatus());
        } catch (Exception e) {
            throw new IllegalArgumentException("Unknown payment status: " + response.getMethod());
        }

        builder.method(FastpayEnumConverter.convert(fastpayPaymentMethod));
        builder.status(FastpayEnumConverter.convert(fastpayPaymentStatus));

        return builder.build();
    }

}
