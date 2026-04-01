package com._s.api.domain.rentItem;

import com._s.api.domain.product.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RentItem {
    private String id;

    private Product product;

    private BigDecimal unitValue;

    private BigDecimal subTotal;

    private Integer quantity;

    private String rentId;

    public RentItem(Product product, BigDecimal unitValue, Integer quantity) {
        this.product = product;
        this.unitValue = unitValue;
        this.quantity = quantity;
    }

    public static RentItem mount(
            String id,
            Product product,
            BigDecimal unitValue,
            BigDecimal subTotal,
            Integer quantity,
            String rentId
    ) {
        return new RentItem(id, product, unitValue, subTotal, quantity, rentId);
    }

    public void calculateSubTotal() {
        if (unitValue == null || quantity == null) {
            this.subTotal = BigDecimal.ZERO;
            return;
        }

        this.subTotal = unitValue.multiply(BigDecimal.valueOf(quantity));
    }
}
