package com._s.api.presentation.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
    private String name;

    private String description;

    @Positive
    private BigDecimal price;

    @Positive
    private Integer stock;

    private String imageUri;
}
