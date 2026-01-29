package com._s.api.domain.orderItem;

import com._s.api.domain.order.Order;
import com._s.api.domain.product.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderItem {
    private String id;

    private Product product;

    private BigDecimal unitValue;

    private BigDecimal subTotal;

    private Integer quantity;

    private String orderId;

    public OrderItem(Product product, BigDecimal unitValue, Integer quantity) {
        this.product = product;
        this.unitValue = unitValue;
        this.quantity = quantity;
    }

    public static OrderItem mount(String id, Product product, BigDecimal unitValue, BigDecimal subTotal, Integer quantity, String orderId) {
        return new OrderItem(id, product, unitValue, subTotal, quantity, orderId);
    }

    public void calculateSubTotal() {
        if (unitValue == null || quantity == null) {
            this.subTotal = BigDecimal.ZERO;
            return;
        }
        this.subTotal = unitValue.multiply(BigDecimal.valueOf(quantity));
    }
}
