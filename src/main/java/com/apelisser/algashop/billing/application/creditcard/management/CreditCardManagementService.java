package com.apelisser.algashop.billing.application.creditcard.management;

import com.apelisser.algashop.billing.domail.model.creditcard.CreditCard;
import com.apelisser.algashop.billing.domail.model.creditcard.CreditCardNotFoundException;
import com.apelisser.algashop.billing.domail.model.creditcard.CreditCardProviderService;
import com.apelisser.algashop.billing.domail.model.creditcard.CreditCardRepository;
import com.apelisser.algashop.billing.domail.model.creditcard.LimitedCreditCard;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CreditCardManagementService {

    private final CreditCardRepository creditCardRepository;
    private final CreditCardProviderService creditCardProviderService;

    public CreditCardManagementService(CreditCardRepository creditCardRepository,
        CreditCardProviderService creditCardProviderService) {
        this.creditCardRepository = creditCardRepository;
        this.creditCardProviderService = creditCardProviderService;
    }

    @Transactional
    public UUID register(TokenizedCreditCardInput input) {
        LimitedCreditCard limitedCreditCard = creditCardProviderService.register(
            input.getCustomerId(),
            input.getTokenizedCard());

        CreditCard creditCard = CreditCard.brandNew(
            input.getCustomerId(),
            limitedCreditCard.getLastNumbers(),
            limitedCreditCard.getBrand(),
            limitedCreditCard.getExpMonth(),
            limitedCreditCard.getExpYear(),
            limitedCreditCard.getGatewayCode()
        );

        creditCardRepository.save(creditCard);
        return creditCard.getId();
    }

    @Transactional
    public void delete(UUID customerId, UUID creditCardId) {
        CreditCard creditCard = creditCardRepository.findByCustomerIdAndId(customerId, creditCardId)
            .orElseThrow(CreditCardNotFoundException::new);

        creditCardRepository.delete(creditCard);
        creditCardProviderService.delete(creditCard.getGatewayCode());
    }

}
