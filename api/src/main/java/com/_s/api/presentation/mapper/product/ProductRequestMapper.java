package com._s.api.presentation.mapper.product;

import com._s.api.domain.product.service.CreateProductCommand;
import com._s.api.domain.product.service.UpdateProductCommand;
import com._s.api.presentation.dto.CreateProductRequest;
import com._s.api.presentation.dto.UpdateProductRequest;

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

    public static UpdateProductCommand toUpdateCommand(UpdateProductRequest request, String id) {
        return new UpdateProductCommand(
                id,
                request.getName(),
                request.getDescription(),
                request.getStock(),
                request.getImageUri(),
                request.getPrice()
        );
    }

}
