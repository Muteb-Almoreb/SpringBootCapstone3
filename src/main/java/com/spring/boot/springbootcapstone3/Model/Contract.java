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
public class Contract { // created by Abdullah Alwael

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "totalPrice should not be empty")
    @Column(columnDefinition = "double not null")
    private double totalPrice;

    @NotNull(message = "startDate should not be empty")
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime startDate;

    @NotNull(message = "endDate should not be empty")
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime endDate;

    @NotEmpty(message = "careScope should not be empty")
    @Column(columnDefinition = "varchar(30) not null")
    private String careScope; // JSON?

    @NotEmpty(message = "status should not be empty")
    @Column(columnDefinition = "varchar(30) not null")
    private String status;

    @NotEmpty(message = "contractLocationName should not be empty")
    @Column(columnDefinition = "varchar(30) not null")
    private String contractLocationName;

    @NotEmpty(message = "contractItemsJson should not be empty")
    @Column(columnDefinition = "varchar(30) not null")
    private String contractItemsJson;// JSON?

    @NotNull(message = "requestId should not be empty")
    @Column(columnDefinition = "int not null")
    private Integer requestId;

    @NotNull(message = "organizationId should not be empty")
    @Column(columnDefinition = "int not null")
    private Integer organizationId;

    @NotNull(message = "vendorId should not be empty")
    @Column(columnDefinition = "int not null")
    private Integer vendorId;
}
