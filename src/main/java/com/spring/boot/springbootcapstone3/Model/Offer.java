package com.spring.boot.springbootcapstone3.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class Offer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotNull( message = "Price cant be null")
    @Column(columnDefinition = "double not null default 0")
    private Double price = 0.0;



    @NotEmpty(message = "The title must not be empty")
    @Column(columnDefinition = "varchar(100) not null")
    private String title;

    @NotEmpty(message = "The description must not be empty")
    @Column(columnDefinition = "varchar(200) not null")
    private String description;


    @Pattern(
            regexp = "(?i)^(PENDING|ACCEPTED|REJECTED|WITHDRAWN)$",
            message = "Status must be one of: PENDING, ACCEPTED, REJECTED, WITHDRAWN"
    )
    @Column(columnDefinition = "varchar(20) not null")
    private String status = "PENDING";


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(insertable = false,
            updatable = false,
            columnDefinition = "datetime not null default current_timestamp")
    private LocalDateTime createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id", unique = true)
    @JsonIgnore
    private Contract contract;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "service_request_id", nullable = false)
    @JsonIgnore
    private ServiceRequest serviceRequest;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    @JsonIgnore
    private Vendor vendor;






}
