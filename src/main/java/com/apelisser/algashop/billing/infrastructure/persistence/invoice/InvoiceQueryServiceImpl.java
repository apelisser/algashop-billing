package com.apelisser.algashop.billing.infrastructure.persistence.invoice;

import com.apelisser.algashop.billing.application.invoice.query.InvoiceOutput;
import com.apelisser.algashop.billing.application.invoice.query.InvoiceQueryService;
import com.apelisser.algashop.billing.application.utility.Mapper;
import com.apelisser.algashop.billing.domail.model.invoice.Invoice;
import com.apelisser.algashop.billing.domail.model.invoice.InvoiceNotFoundException;
import com.apelisser.algashop.billing.domail.model.invoice.InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class InvoiceQueryServiceImpl implements InvoiceQueryService {

    private final InvoiceRepository invoiceRepository;
    private final Mapper mapper;

    public InvoiceQueryServiceImpl(InvoiceRepository invoiceRepository, Mapper mapper) {
        this.invoiceRepository = invoiceRepository;
        this.mapper = mapper;
    }

    @Override
    public InvoiceOutput findByOrderId(String orderId) {
        Invoice invoice = invoiceRepository.findByOrderId(orderId).orElseThrow(InvoiceNotFoundException::new);
        return mapper.convert(invoice, InvoiceOutput.class);
    }

}
