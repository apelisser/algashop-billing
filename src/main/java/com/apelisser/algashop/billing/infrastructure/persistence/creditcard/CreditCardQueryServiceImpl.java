package com.apelisser.algashop.billing.infrastructure.persistence.creditcard;

import com.apelisser.algashop.billing.application.creditcard.query.CreditCardOutput;
import com.apelisser.algashop.billing.application.creditcard.query.CreditCardQueryService;
import com.apelisser.algashop.billing.application.utility.Mapper;
import com.apelisser.algashop.billing.domail.model.creditcard.CreditCardNotFoundException;
import com.apelisser.algashop.billing.domail.model.creditcard.CreditCardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CreditCardQueryServiceImpl implements CreditCardQueryService {

    private final CreditCardRepository creditCardRepository;
    private final Mapper mapper;

    public CreditCardQueryServiceImpl(CreditCardRepository creditCardRepository, Mapper mapper) {
        this.creditCardRepository = creditCardRepository;
        this.mapper = mapper;
    }

    @Override
    public CreditCardOutput findOne(UUID customerId, UUID creditCardId) {
        return creditCardRepository.findByCustomerIdAndId(customerId, creditCardId)
            .map(creditCard -> mapper.convert(creditCard, CreditCardOutput.class))
            .orElseThrow(CreditCardNotFoundException::new);
    }

    @Override
    public List<CreditCardOutput> findByCustomer(UUID customerId) {
        return creditCardRepository.findAllByCustomerId(customerId).stream()
            .map(creditCard -> mapper.convert(creditCard, CreditCardOutput.class))
            .toList();
    }

}
