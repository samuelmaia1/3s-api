package com._s.api.presentation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateRentRequest {

    @NotBlank
    private String costumerId;

    @NotEmpty
    private List<CreateRentItemRequest> items;

    @Valid
    private AddressRequest deliveryAddress;

    private LocalDateTime deliveryDate;

    private LocalDateTime returnDate;

    @NotNull
    private BigDecimal deliveryTax;
}
