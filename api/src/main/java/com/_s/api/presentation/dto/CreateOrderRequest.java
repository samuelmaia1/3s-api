package com._s.api.presentation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateOrderRequest {

    @NotBlank
    private String costumerId;

    @NotEmpty
    private List<CreateOrderItemRequest> items;

    @Valid
    @NotNull
    private AddressRequest deliveryAddress;

    @NotNull
    private LocalDateTime deliveryDate;

    @NotNull
    private LocalDateTime returnDate;
}
