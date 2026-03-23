package com._s.api.presentation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
    private AddressRequest deliveryAddress;

    private LocalDateTime deliveryDate;

    private LocalDateTime returnDate;
}
