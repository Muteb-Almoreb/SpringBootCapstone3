package com.spring.boot.springbootcapstone3.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartiesDTO {
    private OrganizationSummaryDTO organization;
    private VendorSummaryDTO vendor;
}
