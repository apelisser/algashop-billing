package com.apelisser.algashop.billing.presentation.invoice;

import com.apelisser.algashop.billing.application.invoice.management.GenerateInvoiceInput;
import com.apelisser.algashop.billing.application.invoice.management.InvoiceManagementApplicationService;
import com.apelisser.algashop.billing.application.invoice.query.InvoiceOutput;
import com.apelisser.algashop.billing.application.invoice.query.InvoiceQueryService;
import com.apelisser.algashop.billing.domail.model.creditcard.CreditCardNotFoundException;
import com.apelisser.algashop.billing.presentation.UnprocessableEntityException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders/{orderId}/invoice")
public class InvoiceController {

    private static final Logger log = LoggerFactory.getLogger(InvoiceController.class);

    private final InvoiceQueryService invoiceQueryService;
    private final InvoiceManagementApplicationService invoiceManagementApplicationService;

    public InvoiceController(InvoiceQueryService invoiceQueryService,
            InvoiceManagementApplicationService invoiceManagementApplicationService) {
        this.invoiceQueryService = invoiceQueryService;
        this.invoiceManagementApplicationService = invoiceManagementApplicationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceOutput generate(@PathVariable String orderId, @Valid @RequestBody GenerateInvoiceInput input) {
        input.setOrderId(orderId);

        UUID invoiceId;
        try {
            invoiceId = invoiceManagementApplicationService.generate(input);
        } catch (CreditCardNotFoundException e) {
            throw new UnprocessableEntityException(e.getMessage(), e);
        }

        try {
            invoiceManagementApplicationService.processPayment(invoiceId);
        } catch (Exception e) {
            log.error("Error when processing payment for invoice {}", invoiceId, e);
        }

        return invoiceQueryService.findByOrderId(orderId);
    }

    @GetMapping
    public InvoiceOutput findByOrder(@PathVariable String orderId) {
        return invoiceQueryService.findByOrderId(orderId);
    }

}
