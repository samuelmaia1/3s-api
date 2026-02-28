package com._s.api.domain.order;

import com._s.api.domain.orderItem.OrderItem;
import com._s.api.domain.shared.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private Address deliveryAddress;

    private LocalDateTime deliveryDate;

    private LocalDateTime returnDate;

    private List<OrderItem> items = new ArrayList<>();

    public Order(
            String userId,
            String costumerId,
            OrderStatus status,
            List<OrderItem> items,
            Address deliveryAddress,
            LocalDateTime deliveryDate,
            LocalDateTime returnDate
    ) {
        this.userId = userId;
        this.costumerId = costumerId;
        this.status = status;
        this.items = items;
        this.deliveryAddress = deliveryAddress;
        this.deliveryDate = deliveryDate;
        this.returnDate = returnDate;
    }

    public static Order mount(
            String id,
            String userId,
            String costumerId,
            LocalDateTime createdAt,
            OrderStatus status,
            BigDecimal total,
            List<OrderItem> items,
            Address deliveryAddress,
            LocalDateTime deliveryDate,
            LocalDateTime returnDate
            ) {
        return new Order(id, userId, costumerId, createdAt, status, total, deliveryAddress, deliveryDate, returnDate, items);
    }

    public void calculateTotal() {
        if (items == null || items.isEmpty()) {
            this.total = BigDecimal.ZERO;
            return;
        }

        this.total = items.stream()
                .map(OrderItem::getSubTotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
