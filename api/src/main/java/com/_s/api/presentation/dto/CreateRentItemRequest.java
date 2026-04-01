package com._s.api.presentation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateRentItemRequest {
    @NotNull
    private String productId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
