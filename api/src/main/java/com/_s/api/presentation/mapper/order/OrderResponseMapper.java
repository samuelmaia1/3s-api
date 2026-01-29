package com._s.api.presentation.mapper.order;

import com._s.api.domain.order.Order;
import com._s.api.presentation.response.OrderResponse;

public class OrderResponseMapper {
    public static OrderResponse toResponse(Order order) {
        return new OrderResponse(order);
    }
}
