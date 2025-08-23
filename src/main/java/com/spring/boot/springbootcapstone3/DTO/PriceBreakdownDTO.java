package com.spring.boot.springbootcapstone3.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceBreakdownDTO {

    private Double basePrice;            // سعر العرض (قبل العمولة)
    private Double platformFee;          // عمولتنا
    private Double platformFeePercent;   // نسبة العمولة (مثلاً 20.0)
    private Double totalPrice;
}
