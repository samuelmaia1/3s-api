package com._s.api.domain.order.service;

import com._s.api.presentation.dto.CreateOrderRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderCommand {
    private CreateOrderRequest request;
}
