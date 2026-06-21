package com.apelisser.algashop.billing.infrastructure.creditcard.fastpay;

import com.apelisser.algashop.billing.domail.model.creditcard.LimitedCreditCard;
import com.apelisser.algashop.billing.infrastructure.AbstractFastpayIT;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CreditCardProviderServiceFastpayImplIT extends AbstractFastpayIT {

    @BeforeAll
    public static void setUpAll() {
        AbstractFastpayIT.startWireMock();
    }

    @AfterAll
    public static void tearDownAll() {
        AbstractFastpayIT.stopWireMock();
    }

    @Test
    public void shouldRegisterCreditCard() {
        LimitedCreditCard limitedCreditCard = registerCard();
        Assertions.assertThat(limitedCreditCard.getGatewayCode()).isNotBlank();
    }

    @Test
    public void shouldFindRegisteredCreditCard() {
        LimitedCreditCard limitedCreditCard = registerCard();
        LimitedCreditCard limitedCreditCardFound = creditCardProvider.findById(limitedCreditCard.getGatewayCode())
            .orElseThrow();

        Assertions.assertThat(limitedCreditCardFound.getGatewayCode()).isEqualTo(limitedCreditCard.getGatewayCode());
    }

    @Test
    public void shouldDeleteRegisteredCreditCard() {
        LimitedCreditCard limitedCreditCard = registerCard();

        creditCardProvider.delete(limitedCreditCard.getGatewayCode());
    }

}