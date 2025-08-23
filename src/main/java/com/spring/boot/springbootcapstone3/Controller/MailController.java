package com.spring.boot.springbootcapstone3.Controller;

import com.spring.boot.springbootcapstone3.API.ApiResponse;
import com.spring.boot.springbootcapstone3.Model.Contract;
import com.spring.boot.springbootcapstone3.Repository.ContractRepository;
import com.spring.boot.springbootcapstone3.Service.ContractService;
import com.spring.boot.springbootcapstone3.Service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final EmailService emailService;

    private final ContractService contractService;
    private ContractRepository contractRepository;

    @GetMapping("/sendMail")
    public ResponseEntity<?> sendMail() {
        emailService.sendEmail("muteb.almoreb@gmail.com", "Hello from Spring Boot", "محاولة الى ايميل مختلف");
        return  ResponseEntity.status(200).body( new ApiResponse("Email sent!"));
    }


    @PostMapping("/{id}/email-html")
    public ResponseEntity<Void> emailPdfHtml(@PathVariable Integer id) {
        File pdf = contractService.generateContractPdfFile(id);
        Contract c = contractRepository.findContractById(id);
        String to = c.getOffer().getVendor().getEmail();
        String vendorName = c.getOffer().getVendor().getName();

        emailService.sendEmailWithContractHtml(to, "Contract", id, vendorName, pdf);
        return ResponseEntity.accepted().build();
    }




}
