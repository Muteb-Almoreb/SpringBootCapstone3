package com.spring.boot.springbootcapstone3.Controller;

import com.spring.boot.springbootcapstone3.API.ApiResponse;
import com.spring.boot.springbootcapstone3.DTO.ContractPrintResponse;
import com.spring.boot.springbootcapstone3.Model.Contract;
import com.spring.boot.springbootcapstone3.Service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;

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

    // extra by abdullah
    @GetMapping("/filter/date-between/{startDate}/{endDate}/{vendorId}")
    public ResponseEntity<?> getContractsBetweenDates(@PathVariable LocalDate startDate,
                                                      @PathVariable LocalDate endDate,
                                                      @PathVariable Integer vendorId){
        return ResponseEntity.ok(contractService.getContractsBetweenDates(startDate, endDate, vendorId));
    }

    // extra by abdullah
    @GetMapping("/filter/overdue/{vendorId}")
    public ResponseEntity<?> getOverdueContracts(@PathVariable Integer vendorId){
        return ResponseEntity.ok(contractService.getOverdueContracts(vendorId));
    }

    // extra by abdullah
    @GetMapping("/filter/almost-expired/{vendorId}")
    public ResponseEntity<?> getAlmostExpiredContracts(@PathVariable Integer vendorId){
        return ResponseEntity.ok(contractService.getAlmostExpiredContracts(vendorId));
    }

    // extra by abdullah
    @PutMapping("/renew/{vendorId}/{contractId}")
    public ResponseEntity<?> renewContract(@PathVariable Integer vendorId, @PathVariable Integer contractId){
        contractService.renewContract(vendorId, contractId);
        return ResponseEntity.ok(new ApiResponse("Contract renewed successfully"));
    }

    @GetMapping("/{id}/print/pdf")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Integer id) throws IOException {
        File pdf = contractService.generateContractPdfFile(id); // your single-method generator (returns File)

        byte[] bytes = Files.readAllBytes(pdf.toPath());

        String filename = "contract-" + id + ".pdf";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(bytes.length)
                .body(bytes);
    }

    /**
     * Generate the PDF and send it by email using your existing sender.
     * Adjust the method call to match your sender’s signature.
     */
    @PostMapping("/{id}/email")
    public ResponseEntity<Void> emailPdf(@PathVariable Integer id,
                                         @RequestParam String to,
                                         @RequestParam(defaultValue = "Contract") String subject,
                                         @RequestParam(defaultValue = "Please find the contract attached.") String body) {
        File pdf = contractService.generateContractPdfFile(id);

        // ---- Call your existing email sender here ----
        // Example signatures (replace with your real one):
        // mailService.sendWithAttachment(to, subject, body, pdf);                        // if it accepts File
        // mailService.sendWithAttachment(to, subject, body, pdf, "contract-"+id+".pdf"); // if it needs a filename

        return ResponseEntity.accepted().build();
    }



}
