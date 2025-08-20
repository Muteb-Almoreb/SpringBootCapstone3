//package com.spring.boot.springbootcapstone3.DTO;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Setter
//@Getter
//@AllArgsConstructor
//public class ContractDTOIn {
//
//    @NotNull(message = "totalPrice should not be empty")
//    @Column(columnDefinition = "double not null")
//    private double totalPrice;
//
//    @NotNull(message = "startDate should not be empty")
//    private LocalDateTime startDate;
//
//    @NotNull(message = "endDate should not be empty")
//    private LocalDateTime endDate;
//
//    @NotNull(message = "serviceRequestId should not be empty")
//    private Integer serviceRequestId;
//
//    @NotNull(message = "offerId should not be empty")
//    private Integer offerId;
//}
