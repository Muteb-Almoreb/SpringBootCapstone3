package com.spring.boot.springbootcapstone3.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferDTO {
    private Integer id; // اختياري/للإخراج

    @NotNull
    @PositiveOrZero
    private Double price;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;
}
