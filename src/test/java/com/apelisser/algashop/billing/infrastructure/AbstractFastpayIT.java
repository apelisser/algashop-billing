package com.apelisser.algashop.billing.infrastructure;

import com.apelisser.algashop.billing.domail.model.creditcard.LimitedCreditCard;
import com.apelisser.algashop.billing.infrastructure.creditcard.fastpay.CreditCardProviderServiceFastpayImpl;
import com.apelisser.algashop.billing.infrastructure.creditcard.fastpay.FastpayCreditCardTokenizationAPIClient;
import com.apelisser.algashop.billing.infrastructure.creditcard.fastpay.FastpayCreditCardTokenizationAPIClientConfig;
import com.apelisser.algashop.billing.infrastructure.creditcard.fastpay.FastpayTokenizationInput;
import com.apelisser.algashop.billing.infrastructure.creditcard.fastpay.FastpayTokenizedCreditCardModel;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ClasspathFileSource;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.extension.responsetemplating.TemplateEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.time.Year;
import java.util.Collections;
import java.util.UUID;

@Import(FastpayCreditCardTokenizationAPIClientConfig.class)
public abstract class AbstractFastpayIT extends AbstractInfrastructureIT {

    @Autowired
    protected CreditCardProviderServiceFastpayImpl creditCardProvider;

    @Autowired
    protected FastpayCreditCardTokenizationAPIClient tokenizationAPIClient;

    // Source: https://github.com/algaworks/fastpay
    protected static final String alwaysPaidCardNumber = "4622943127011022";
    protected static final UUID validCustomerId = UUID.randomUUID();

    protected static WireMockServer wireMockFastpay;

    public static void startWireMock() {
        wireMockFastpay = new WireMockServer(WireMockConfiguration.options()
            .port(8788)
            .usingFilesUnderDirectory("src/test/resources/wiremock/fastpay")
            .extensions(new ResponseTemplateTransformer(
                TemplateEngine.defaultTemplateEngine(),
                true,
                new ClasspathFileSource("src/test/resources/wiremock/fastpay"),
                Collections.emptyList()
            ))
        );
        wireMockFastpay.start();
    }

    public static void stopWireMock() {
        wireMockFastpay.stop();
    }

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
