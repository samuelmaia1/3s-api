package com._s.api.presentation.response;

import com._s.api.domain.product.Product;
import com._s.api.domain.rentItem.RentItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class RentItemResponse {
    private String id;

    private Product product;

    private BigDecimal unitValue;

    private BigDecimal subTotal;

    private Integer quantity;

    public RentItemResponse(RentItem item) {
        this.id = item.getId();
        this.product = item.getProduct();
        this.unitValue = item.getUnitValue();
        this.subTotal = item.getSubTotal();
        this.quantity = item.getQuantity();
    }
}
