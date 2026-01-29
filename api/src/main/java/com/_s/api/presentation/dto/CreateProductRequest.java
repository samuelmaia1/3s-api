package com._s.api.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Positive
    @NotNull
    private BigDecimal price;

    @Positive
    @NotNull
    private Integer stock;

    private String imageUri;
}
