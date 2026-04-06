package com._s.api.presentation.response;

import com._s.api.domain.product.service.GetProductDetailsService;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductRecentTransactionResponse {
    private String id;
    private String code;
    private GetProductDetailsService.TransactionType type;
    private LocalDateTime createdAt;
    private String status;
    private BigDecimal total;
    private BigDecimal productTotal;
    private BigDecimal unitValue;
    private Integer quantity;
    private LocalDateTime deliveryDate;
    private LocalDateTime returnDate;
    private CostumerSummaryResponse costumer;

    public ProductRecentTransactionResponse(GetProductDetailsService.ProductTransaction transaction) {
        this.id = transaction.id();
        this.code = transaction.code();
        this.type = transaction.type();
        this.createdAt = transaction.createdAt();
        this.status = transaction.status();
        this.total = transaction.total();
        this.productTotal = transaction.productTotal();
        this.unitValue = transaction.unitValue();
        this.quantity = transaction.quantity();
        this.deliveryDate = transaction.deliveryDate();
        this.returnDate = transaction.returnDate();
        this.costumer = transaction.costumer() == null ? null : new CostumerSummaryResponse(transaction.costumer());
    }
}
