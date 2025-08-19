package com.spring.boot.springbootcapstone3.Service;

import com.spring.boot.springbootcapstone3.API.ApiException;
import com.spring.boot.springbootcapstone3.DTO.InvoiceDTOIn;
import com.spring.boot.springbootcapstone3.Model.Contract;
import com.spring.boot.springbootcapstone3.Model.Invoice;
import com.spring.boot.springbootcapstone3.Repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService { // Created by Abdullah Alwael
    private final InvoiceRepository invoiceRepository;
    private final ContractService contractService;

    public void addInvoice(InvoiceDTOIn invoiceDTOIn){
        Contract contract = contractService.getContract(invoiceDTOIn.getContractId());

        if (contract == null){
            throw new ApiException("Error, contract not found");
        }

        Invoice invoice = new Invoice(null, invoiceDTOIn.getToEmail(), contract.getTotalPrice()
                , LocalDateTime.now(), invoiceDTOIn.getPaymentMethod(), null
                , invoiceDTOIn.getProviderReference(), contract);

        invoiceRepository.save(invoice);
    }

    public List<Invoice> getInvoices(){
        return invoiceRepository.findAll();
    }

    public Invoice getInvoice(Integer invoiceId){
        return invoiceRepository.findInvoiceById(invoiceId);
    }

    public void updateInvoice(InvoiceDTOIn invoiceDTOIn){
        Invoice oldInvoice = getInvoice(invoiceDTOIn.getContractId());

        if (oldInvoice == null){
            throw new ApiException("Error, invoice not found");
        }

//        oldInvoice.setAmount(invoiceDTOIn.getAmount()); // the amount should not be updated
        // if it needs to be updated then delete the invoice and create it again.
//        oldInvoice.setStatus(invoiceDTOIn.getStatus());
//        oldInvoice.setPaidAt(invoiceDTOIn.getPaidAt()); // should paidAt be updated?
//        oldInvoice.setIssuedAt(invoiceDTOIn.getIssuedAt()); // creation date should not be updated
        oldInvoice.setPaymentMethod(invoiceDTOIn.getPaymentMethod());
        oldInvoice.setProviderReference(invoiceDTOIn.getProviderReference());
        oldInvoice.setToEmail(invoiceDTOIn.getToEmail());

        invoiceRepository.save(oldInvoice);
    }

    public void deleteInvoice(Integer invoiceId){
        // should invoice be deleted?

        Invoice oldInvoice = getInvoice(invoiceId);
        if (oldInvoice == null){
            throw new ApiException("Error, invoice not found");
        }

        Contract contract = oldInvoice.getContract();
        if (contract == null){
            throw new ApiException("Error, contract not found");
        }

        contract.setInvoice(null); // to remove the relationship
        invoiceRepository.delete(oldInvoice);
    }
}
