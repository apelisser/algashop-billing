package com.apelisser.algashop.billing.domail.model.creditcard;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CreditCardRepository extends JpaRepository<CreditCard, UUID> {

}
