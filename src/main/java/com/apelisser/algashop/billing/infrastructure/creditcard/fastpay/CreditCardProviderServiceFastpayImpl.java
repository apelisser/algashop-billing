package com.apelisser.algashop.billing.infrastructure.creditcard.fastpay;

import com.apelisser.algashop.billing.domail.model.creditcard.CreditCardProviderService;
import com.apelisser.algashop.billing.domail.model.creditcard.LimitedCreditCard;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@ConditionalOnProperty(name = "algashop.integrations.payment.provider", havingValue = "FASTPAY")
public class CreditCardProviderServiceFastpayImpl implements CreditCardProviderService {

    private final FastpayCreditCardAPIClient fastpayCreditCardAPIClient;

    public CreditCardProviderServiceFastpayImpl(FastpayCreditCardAPIClient fastpayCreditCardAPIClient) {
        this.fastpayCreditCardAPIClient = fastpayCreditCardAPIClient;
    }

    @Override
    public LimitedCreditCard register(UUID customerId, String tokenizedCard) {
        var input = FastpayCreditCardInput.builder()
            .tokenizedCard(tokenizedCard)
            .customerCode(customerId.toString())
            .build();

        FastpayCreditCardResponse response = fastpayCreditCardAPIClient.create(input);

        return toLimitedCreditCard(response);
    }

    @Override
    public Optional<LimitedCreditCard> findById(String gatewayCode) {
        FastpayCreditCardResponse response = fastpayCreditCardAPIClient.findById(gatewayCode);
        return Optional.of(toLimitedCreditCard(response));
    }

    @Override
    public void delete(String gatewayCode) {
        fastpayCreditCardAPIClient.delete(gatewayCode);
    }

    private LimitedCreditCard toLimitedCreditCard(FastpayCreditCardResponse response) {
        return LimitedCreditCard.builder()
            .gatewayCode(response.getId())
            .lastNumbers(response.getLastNumbers())
            .expMonth(response.getExpMonth())
            .expYear(response.getExpYear())
            .brand(response.getBrand())
            .build();
    }

}
