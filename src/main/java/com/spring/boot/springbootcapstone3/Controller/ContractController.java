/*
package com.spring.boot.springbootcapstone3.Controller;

import com.spring.boot.springbootcapstone3.API.ApiResponse;
import com.spring.boot.springbootcapstone3.DTO.ContractDTOIn;
import com.spring.boot.springbootcapstone3.Model.Contract;
import com.spring.boot.springbootcapstone3.Service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contract")
@RequiredArgsConstructor
public class ContractController { // Created by Abdullah Alwael
    private final ContractService contractService;

    @PostMapping("/add")
    public ResponseEntity<?> addContract(@Valid @RequestBody ContractDTOIn contractDTOIn){
        contractService.addContract(contractDTOIn);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Contract added successfully"));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getContracts(){
        return ResponseEntity.status(HttpStatus.OK).body(contractService.getContracts());

    }

    // the id is the same as the request id
    @PutMapping("/update")
    public ResponseEntity<?> updateContract(@Valid @RequestBody ContractDTOIn contractDTOIn){
        contractService.updateContract(contractDTOIn);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Contract updated successfully"));

    }

    @DeleteMapping("/delete/{contractId}")
    public ResponseEntity<?> deleteContract(@PathVariable Integer contractId){
        contractService.deleteContract(contractId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Contract deleted successfully"));

    }
}
*/
