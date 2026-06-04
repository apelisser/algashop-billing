package com.apelisser.algashop.billing.presentation.creditcard;

import com.apelisser.algashop.billing.application.creditcard.management.CreditCardManagementService;
import com.apelisser.algashop.billing.application.creditcard.management.TokenizedCreditCardInput;
import com.apelisser.algashop.billing.application.creditcard.query.CreditCardOutput;
import com.apelisser.algashop.billing.application.creditcard.query.CreditCardQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers/{customerId}/credit-cards")
public class CreditCardController {

    private final CreditCardManagementService creditCardManagementService;
    private final CreditCardQueryService creditCardQueryService;

    public CreditCardController(CreditCardManagementService creditCardManagementService,
            CreditCardQueryService creditCardQueryService) {
        this.creditCardManagementService = creditCardManagementService;
        this.creditCardQueryService = creditCardQueryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCardOutput register(@PathVariable UUID customerId, @RequestBody TokenizedCreditCardInput input) {
        input.setCustomerId(customerId);
        UUID creditCardId = creditCardManagementService.register(input);
        return creditCardQueryService.findOne(customerId, creditCardId);
    }

    @GetMapping
    public List<CreditCardOutput> findAllByCustomerId(@PathVariable UUID customerId) {
        return creditCardQueryService.findByCustomer(customerId);
    }

    @GetMapping("/{creditCardId}")
    public CreditCardOutput findOne(@PathVariable UUID customerId, @PathVariable UUID creditCardId) {
        return creditCardQueryService.findOne(customerId, creditCardId);
    }

    @DeleteMapping("/{creditCardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID customerId, @PathVariable UUID creditCardId) {
        creditCardManagementService.delete(customerId, creditCardId);
    }

}
