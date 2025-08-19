package com.spring.boot.springbootcapstone3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Invoice { // Created by Abdullah Alwael

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "contractId should not be empty")
    @Column(columnDefinition = "int not null")
    private Integer contractId;
    
    @NotEmpty(message = "toEmail should not be empty")
    @Column(columnDefinition = "varchar(30) not null")
    private String toEmail;

    // calculated by the system
    @Column(columnDefinition = "double not null")
    private double amount;

    // calculated by the system
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime issuedAt;
    
    // calculated by the system
    @Column(columnDefinition = "varchar(30) not null")
    private String status;
    
    @NotEmpty(message = "paymentMethod should not be empty")
    @Column(columnDefinition = "varchar(30) not null")
    private String paymentMethod;

    // calculated by the system
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime paidAt;
    
    @NotEmpty(message = "providerReference should not be empty")
    @Column(columnDefinition = "varchar(30) not null")
    private String providerReference;

}
