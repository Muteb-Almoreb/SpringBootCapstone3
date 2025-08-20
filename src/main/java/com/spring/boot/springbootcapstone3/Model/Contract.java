package com.spring.boot.springbootcapstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    private double price;

    @NotNull(message = "startDate should not be empty")
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime startDate;

    @NotNull(message = "endDate should not be empty")
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime endDate;

    @OneToOne(mappedBy = "contract")
    @JsonIgnore
    private Offer offer;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
//    @JoinColumn(nullable = false, unique = true)
    @JsonIgnore
    private ServiceRequest serviceRequest;

}
