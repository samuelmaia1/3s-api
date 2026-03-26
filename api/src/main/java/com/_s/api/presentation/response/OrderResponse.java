package com._s.api.presentation.response;

import com._s.api.domain.order.Order;
import com._s.api.domain.order.OrderStatus;
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

    private List<OrderItemResponse> items;

    private String costumerId;

    private String userId;

    private String code;

    private CostumerSummaryResponse costumer;

    public OrderResponse(Order order) {
        this.code = order.getCode();
        this.id = order.getId();
        this.createdAt = order.getCreatedAt();
        this.status = order.getStatus();
        this.total = order.getTotal();
        this.costumerId = order.getCostumerId();
        this.userId = order.getUserId();
        this.items = order.getItems().stream().map(OrderItemResponse::new).toList();
        this.costumer = new CostumerSummaryResponse(order.getCostumer());
    }
}
