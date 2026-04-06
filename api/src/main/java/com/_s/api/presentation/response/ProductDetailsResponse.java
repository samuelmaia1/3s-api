package com._s.api.presentation.response;

import com._s.api.domain.product.service.GetProductDetailsService;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductDetailsResponse extends ProductResponse {
    private List<ProductRecentTransactionResponse> recentTransactions;
    private List<ProductMonthlyRevenueResponse> revenueChart;

    public ProductDetailsResponse(GetProductDetailsService.ProductDetailsData data) {
        super(data.product());
        this.recentTransactions = data.recentTransactions()
                .stream()
                .map(ProductRecentTransactionResponse::new)
                .toList();
        this.revenueChart = data.revenueChart()
                .stream()
                .map(ProductMonthlyRevenueResponse::new)
                .toList();
    }
}
