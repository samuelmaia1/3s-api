package com._s.api.presentation.mapper.product;

import com._s.api.domain.product.service.CreateProductCommand;
import com._s.api.presentation.dto.CreateProductRequest;

public class ProductRequestMapper {
    public static CreateProductCommand toCommand(CreateProductRequest data) {
        CreateProductCommand command = new CreateProductCommand();
        command.setDescription(data.getDescription());
        command.setName(data.getName());
        command.setStock(data.getStock());
        command.setPrice(data.getPrice());
        command.setImageUri(data.getImageUri());

        return command;
    }
}
