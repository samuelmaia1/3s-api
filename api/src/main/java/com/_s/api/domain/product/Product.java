package com._s.api.domain.product;

import com._s.api.domain.product.service.CreateProductCommand;
import com._s.api.domain.product.service.UpdateProductCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    private LocalDateTime createdAt;

    public Product(CreateProductCommand command) {
        this.name = command.getName();
        this.description = command.getDescription();
        this.price = command.getPrice();
        this.stock = command.getStock();
        this.imageUri = command.getImageUri();
    }

    public void update(UpdateProductCommand command) {
        if (command.getDescription() != null)
            this.description = command.getDescription();

        if (command.getPrice() != null)
            this.price = command.getPrice();

        if (command.getStock() != null)
            this.stock = command.getStock();

        if (command.getName() != null)
            this.name = command.getName();

        if (command.getImageUri() != null)
            this.imageUri = command.getImageUri();
    }

    public void decreaseStock(Integer quantity) {
        this.stock = this.stock - quantity;
    }
}
