package com.apelisser.algashop.billing.infrastructure;

import com.apelisser.algashop.billing.domail.model.creditcard.LimitedCreditCard;
import com.apelisser.algashop.billing.infrastructure.creditcard.fastpay.CreditCardProviderServiceFastpayImpl;
import com.apelisser.algashop.billing.infrastructure.creditcard.fastpay.FastpayCreditCardTokenizationAPIClient;
import com.apelisser.algashop.billing.infrastructure.creditcard.fastpay.FastpayCreditCardTokenizationAPIClientConfig;
import com.apelisser.algashop.billing.infrastructure.creditcard.fastpay.FastpayTokenizationInput;
import com.apelisser.algashop.billing.infrastructure.creditcard.fastpay.FastpayTokenizedCreditCardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.time.Year;
import java.util.UUID;

@Import(FastpayCreditCardTokenizationAPIClientConfig.class)
public abstract class AbstractFastpayIT {

    @Autowired
    protected CreditCardProviderServiceFastpayImpl creditCardProvider;

    @Autowired
    protected FastpayCreditCardTokenizationAPIClient tokenizationAPIClient;

    // Source: https://github.com/algaworks/fastpay
    protected static final String alwaysPaidCardNumber = "4622943127011022";
    protected static final UUID validCustomerId = UUID.randomUUID();

    protected LimitedCreditCard registerCard() {
        FastpayTokenizationInput input = FastpayTokenizationInput.builder()
            .number(alwaysPaidCardNumber)
            .cvv("222")
            .expMonth(1)
            .holderName("John Doe")
            .holderDocument("12345")
            .expYear(Year.now().getValue() + 5)
            .build();

        FastpayTokenizedCreditCardModel response = tokenizationAPIClient.tokenize(input);

        return creditCardProvider.register(validCustomerId, response.getTokenizedCard());
    }

}
