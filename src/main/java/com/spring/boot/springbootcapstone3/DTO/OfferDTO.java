package com.spring.boot.springbootcapstone3.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferDTO {
    @NotNull(message = "price is required")
    @PositiveOrZero(message = "price must be zero or positive")
    private Double price;

    @NotEmpty(message = "title must not be empty")
    private String title;

    @NotEmpty(message = "description must not be empty")
    private String description;

}
