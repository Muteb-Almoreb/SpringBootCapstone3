package com.spring.boot.springbootcapstone3.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractSummaryDTO {
    private Integer id;
    private double price;
    private LocalDate startDate;
    private LocalDate endDate;
}
