package com._s.api.domain.order;

import com._s.api.domain.orderItem.OrderItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order {
    private String id;

    private String userId;

    private String costumerId;

    private LocalDateTime createdAt;

    private OrderStatus status;

    private BigDecimal total;

    private List<OrderItem> items = new ArrayList<>();

    public Order(String userId, String costumerId, OrderStatus status, List<OrderItem> items) {
        this.userId = userId;
        this.costumerId = costumerId;
        this.status = status;
        this.items = items;
    }

    public static Order mount(String id, String userId, String costumerId, LocalDateTime createdAt, OrderStatus status, BigDecimal total, List<OrderItem> items) {
        return new Order(id, userId, costumerId, createdAt, status, total, items);
    }

    public void calculateTotal() {
        if (items == null || items.isEmpty()) {
            this.total = BigDecimal.ZERO;
            return;
        }

        this.total = items.stream()
                .map(OrderItem::getSubTotal)
                .filter(subTotal -> subTotal != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
