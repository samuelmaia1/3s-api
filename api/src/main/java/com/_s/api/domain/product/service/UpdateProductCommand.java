package com._s.api.domain.product.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductCommand {
    private String id;
    private String name;
    private String description;
    private Integer stock;
    private String imageUri;
    private BigDecimal price;
}
