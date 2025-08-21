package com.spring.boot.springbootcapstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "totalPrice should not be empty")
    @Column(columnDefinition = "double not null")
    private double price;

    @NotNull(message = "startDate should not be empty")
    @Column(columnDefinition = "datetime not null")
    private LocalDate startDate;

    @NotNull(message = "endDate should not be empty")
    @Column(columnDefinition = "datetime not null")
    private LocalDate endDate;

    @Column(columnDefinition = "varchar(20) not null")
    private String status = "UNPAID";

    @Column(columnDefinition = "varchar(255) not null")
    private String transactionId; // used to store the moyasar transaction id for later use

    @OneToOne(mappedBy = "contract")
    @JsonIgnore
    private Offer offer;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
//    @JoinColumn(nullable = false, unique = true)
    @JsonIgnore
    private ServiceRequest serviceRequest;

}
