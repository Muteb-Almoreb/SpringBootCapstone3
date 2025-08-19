package com.spring.boot.springbootcapstone3.Service;

import com.spring.boot.springbootcapstone3.API.ApiException;
import com.spring.boot.springbootcapstone3.Model.Invoice;
import com.spring.boot.springbootcapstone3.Repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService { // Created by Abdullah Alwael
    private final InvoiceRepository invoiceRepository;

    public void addInvoice(Invoice invoice){
        invoiceRepository.save(invoice);
    }

    public List<Invoice> getInvoices(){
        return invoiceRepository.findAll();
    }

    public Invoice getInvoice(Integer invoiceId){
        return invoiceRepository.findInvoiceById(invoiceId);
    }

    public void updateInvoice(Integer invoiceId, Invoice invoice){
        Invoice oldInvoice = getInvoice(invoiceId);

        if (oldInvoice == null){
            throw new ApiException("Error, invoice not found");
        }

        oldInvoice.setAmount(invoice.getAmount());
        oldInvoice.setContractId(invoice.getContractId());
        oldInvoice.setStatus(invoice.getStatus());
        oldInvoice.setPaidAt(invoice.getPaidAt());
        oldInvoice.setIssuedAt(invoice.getIssuedAt());
        oldInvoice.setPaymentMethod(invoice.getPaymentMethod());
        oldInvoice.setProviderReference(invoice.getProviderReference());
        oldInvoice.setToEmail(invoice.getToEmail());

        invoiceRepository.save(oldInvoice);
    }

    public void deleteInvoice(Integer invoiceId){
        Invoice oldInvoice = getInvoice(invoiceId);

        if (oldInvoice == null){
            throw new ApiException("Error, invoice not found");
        }

        invoiceRepository.delete(oldInvoice);
    }
}
