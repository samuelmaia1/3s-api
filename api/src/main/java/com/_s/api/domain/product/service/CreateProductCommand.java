package com._s.api.domain.product.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductCommand {
    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;

    private String imageUri;
}
