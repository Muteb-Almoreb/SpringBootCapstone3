package com.spring.boot.springbootcapstone3.Service;

import com.spring.boot.springbootcapstone3.API.ApiException;
import com.spring.boot.springbootcapstone3.Model.Contract;
import com.spring.boot.springbootcapstone3.Model.PaymentRequest;
import com.spring.boot.springbootcapstone3.Repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PaymentService { // created by Abdullah Alwael
    @Value("${moyasar.api.key}")
    private String apiKey;

    private static final String MOYASAR_API_URL = "https://api.moyasar.com/v1/payments/";
    private final ContractRepository contractRepository;

    public ResponseEntity<String> processPayment(Integer contractId, PaymentRequest paymentRequest) {

        Contract contract = contractRepository.findContractById(contractId);

        if (contract == null){
            throw new ApiException("Contract not found");
        }

        if(contract.getStatus().equals("paid")){
            throw new ApiException("Contract not found");
        }

        String callBackUrl = "http://localhost:8080/api/v1/payment/callback/"+contract.getId();

        String requestBody = String.format(
                "source[type]=card&source[name]=%s&source[number]=%s&source[cvc]=%s&" +
                        "source[month]=%s&source[year]=%s&amount=%d&currency=%s&" +
                        "callback_url=%s",
                paymentRequest.getName(),
                paymentRequest.getNumber(),
                paymentRequest.getCvc(),
                paymentRequest.getMonth(),
                paymentRequest.getYear(),
                (int) ((contract.getPrice()+contract.getPrice()*0.2)*100),
                // must convert to the smallest currency unit, to add the halala
                // and add the service commission of 20%
                paymentRequest.getCurrency(),
                callBackUrl
        );

        // setting up the HTTP header options
        HttpHeaders headers = new HttpHeaders();

        headers.setBasicAuth(apiKey, "");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // send the request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(MOYASAR_API_URL,
                HttpMethod.POST, entity, String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    public String getPaymentStatus(String paymentId){
        // headers:
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey, "");
        headers.setContentType(MediaType.APPLICATION_JSON);

        // create Request:

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // call Moyasar API:
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                MOYASAR_API_URL + paymentId, HttpMethod.GET, entity, String.class
        );

        // return the response:
        return response.getBody();
    }

    public void updateStatus(Integer contractId, String transaction_id, String status){

        Contract contract = contractRepository.findContractById(contractId);

        if (contract == null){
            throw new ApiException("Contract not found");
        }

        contract.setStatus(status);
        contract.setTransactionId(transaction_id);

        contractRepository.save(contract);
    }
}
