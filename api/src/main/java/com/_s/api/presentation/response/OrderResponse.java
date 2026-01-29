package com._s.api.presentation.response;

import com._s.api.domain.order.Order;
import com._s.api.domain.order.OrderStatus;
import com._s.api.domain.orderItem.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private String id;

    private LocalDateTime createdAt;

    private OrderStatus status;

    private BigDecimal total;

    private List<OrderItem> items;

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.createdAt = order.getCreatedAt();
        this.status = order.getStatus();
        this.total = order.getTotal();
        this.items = order.getItems();
    }
}
