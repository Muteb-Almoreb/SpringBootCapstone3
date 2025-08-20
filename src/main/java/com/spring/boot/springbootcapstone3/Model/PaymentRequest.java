package com.spring.boot.springbootcapstone3.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentRequest { // created by Abdullah Alwael

    /*
     *  Sending cardholder data to the merchant backend is prohibited
     *  and will result in canceling the agreement between Moyasar and the merchant
     *  in addition to the immediate termination of the service.
     *  (but that is exactly what we are doing here)
     */

    private String name;
    private String number;
    private String cvc;
    private String month;
    private String year;
    private String currency;
    private String description;
    private String callbackUrl;
}
