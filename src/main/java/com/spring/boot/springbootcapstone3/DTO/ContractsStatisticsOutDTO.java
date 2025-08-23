package com.spring.boot.springbootcapstone3.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractsStatisticsOutDTO {
    private Long totalActiveContracts;
    private Long totalExpiringSoonContracts;
    private Long totalExpiredContracts;
    private Double totalPaidRevenue;

}
