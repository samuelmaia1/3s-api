package com._s.api.presentation.response;

import com._s.api.domain.product.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductResponse {
    private String id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;

    private String imageUri;

    private LocalDateTime createdAt;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.imageUri = product.getImageUri();
        this.createdAt = product.getCreatedAt();
    }
}
