package com._s.api.presentation.response;

import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.order.OrderStatus;
import com._s.api.presentation.mapper.costumer.CostumerResponseMapper;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseSummary {
    private String id;

    private LocalDateTime createdAt;

    private OrderStatus status;

    private BigDecimal total;

    private List<OrderItemResponse> items;

    private String costumerId;

    private String userId;

    private String code;

    private CostumerResponse costumer;

    public OrderResponseSummary(OrderResponse response, Costumer costumer) {
        this.id = response.getId();
        this.createdAt = response.getCreatedAt();
        this.status = response.getStatus();
        this.total = response.getTotal();
        this.items = response.getItems();
        this.costumerId = response.getCostumerId();
        this.userId = response.getUserId();
        this.costumer = CostumerResponseMapper.toResponse(costumer);
        this.code = response.getCode();
    }
}
