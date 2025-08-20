package com.spring.boot.springbootcapstone3.Controller;

import com.spring.boot.springbootcapstone3.API.ApiResponse;
import com.spring.boot.springbootcapstone3.DTO.ContractPrintResponse;
import com.spring.boot.springbootcapstone3.Model.Contract;
import com.spring.boot.springbootcapstone3.Service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/contract")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @GetMapping("/{id}")
    public ResponseEntity<Contract> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(contractService.getById(id));
    }

    @GetMapping("/by-request/{serviceRequestId}")
    public ResponseEntity<Contract> getByServiceRequest(@PathVariable Integer serviceRequestId) {
        return ResponseEntity.ok(contractService.getByServiceRequestId(serviceRequestId));
    }

    @GetMapping("/by-offer/{offerId}")
    public ResponseEntity<Contract> getByOffer(@PathVariable Integer offerId) {
        return ResponseEntity.ok(contractService.getByOfferId(offerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contract> update(
            @PathVariable Integer id,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        Contract updated = contractService.updateBasicFields(id, price, startDate, endDate);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}/print")
    public ResponseEntity<ContractPrintResponse> print(@PathVariable Integer id) {
        return ResponseEntity.ok(contractService.buildPrintView(id));
    }
}
