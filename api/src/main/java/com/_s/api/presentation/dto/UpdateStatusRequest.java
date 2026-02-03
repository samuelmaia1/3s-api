package com._s.api.presentation.dto;

import com._s.api.domain.order.OrderStatus;

public record UpdateStatusRequest(OrderStatus action) {
}
