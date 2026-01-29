package com._s.api.domain.product;

import com._s.api.domain.product.service.CreateProductCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;

    private String imageUri;

    public Product(CreateProductCommand command) {
        this.name = command.getName();
        this.description = command.getDescription();
        this.price = command.getPrice();
        this.stock = command.getStock();
        this.imageUri = command.getImageUri();
    }
}
