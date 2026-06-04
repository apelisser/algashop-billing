package com.apelisser.algashop.billing.infrastructure.creditcard.fastpay;

import com.apelisser.algashop.billing.domail.model.creditcard.CreditCardProviderService;
import com.apelisser.algashop.billing.domail.model.creditcard.LimitedCreditCard;
import com.apelisser.algashop.billing.presentation.BadGatewayException;
import com.apelisser.algashop.billing.presentation.GatewayTimeoutException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.net.SocketTimeoutException;
import java.net.http.HttpTimeoutException;
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

        FastpayCreditCardResponse response;
        try {
            response = fastpayCreditCardAPIClient.create(input);
        } catch (ResourceAccessException e) {
            throw mapIoException(e);
        }

        return toLimitedCreditCard(response);
    }

    @Override
    public Optional<LimitedCreditCard> findById(String gatewayCode) {
        FastpayCreditCardResponse response;

        try {
            response = fastpayCreditCardAPIClient.findById(gatewayCode);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        } catch (ResourceAccessException e) {
            throw mapIoException(e);
        }

        return Optional.of(toLimitedCreditCard(response));
    }

    @Override
    public void delete(String gatewayCode) {
        try {
            fastpayCreditCardAPIClient.delete(gatewayCode);
        } catch (ResourceAccessException e) {
            throw mapIoException(e);
        }
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

    private RuntimeException mapIoException(ResourceAccessException e) {
        Throwable root = NestedExceptionUtils.getMostSpecificCause(e);

        if (root instanceof HttpTimeoutException || root instanceof SocketTimeoutException) {
            throw new GatewayTimeoutException("Fastpay API timeout", e);
        }

        throw new BadGatewayException("Unable to connect to Fastpay API", e);
    }

}
