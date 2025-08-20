package com.spring.boot.springbootcapstone3.Controller;

import com.spring.boot.springbootcapstone3.Model.PaymentRequest;
import com.spring.boot.springbootcapstone3.Service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<?> processPayment(@Valid @RequestBody PaymentRequest paymentRequest){
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.processPayment(paymentRequest));
    }


    @GetMapping("/get-status/{paymentId}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable String paymentId){
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPaymentStatus(paymentId));
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestBody String body){
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

}
