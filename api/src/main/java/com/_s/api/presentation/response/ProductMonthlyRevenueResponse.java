package com._s.api.presentation.response;

import com._s.api.domain.product.service.GetProductDetailsService;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductMonthlyRevenueResponse {
    private Integer year;
    private Integer month;
    private String reference;
    private BigDecimal total;

    public ProductMonthlyRevenueResponse(GetProductDetailsService.MonthlyRevenueData data) {
        this.year = data.year();
        this.month = data.month();
        this.reference = data.reference();
        this.total = data.total();
    }
}
