package com.spring.boot.springbootcapstone3.Controller;

import com.spring.boot.springbootcapstone3.API.ApiResponse;
import com.spring.boot.springbootcapstone3.Model.Invoice;
import com.spring.boot.springbootcapstone3.Service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/invoice")
@RequiredArgsConstructor
public class InvoiceController { // Created by Abdullah Alwael
    private final InvoiceService invoiceService;

    @PostMapping("/add")
    public ResponseEntity<?> addInvoice(@Valid @RequestBody Invoice invoice){
        invoiceService.addInvoice(invoice);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Invoice added successfully"));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getInvoices(){
        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getInvoices());

    }

    @PutMapping("/update/{invoiceId}")
    public ResponseEntity<?> updateInvoice(@PathVariable Integer invoiceId, @Valid @RequestBody Invoice invoice){
        invoiceService.updateInvoice(invoiceId, invoice);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Invoice updated successfully"));

    }

    @DeleteMapping("/delete/{invoiceId}")
    public ResponseEntity<?> deleteInvoice(@PathVariable Integer invoiceId){
        invoiceService.deleteInvoice(invoiceId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Invoice deleted successfully"));

    }
}
