package com._s.api.presentation.response;

import com._s.api.domain.rent.Rent;
import com._s.api.domain.rent.RentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RentResponse {
    private String id;

    private LocalDateTime createdAt;

    private RentStatus status;

    private BigDecimal total;

    private BigDecimal deliveryTax;

    private LocalDateTime deliveryDate;

    private LocalDateTime returnDate;

    private List<RentItemResponse> items;

    private String costumerId;

    private String userId;

    private String code;

    private CostumerSummaryResponse costumer;

    public RentResponse(Rent rent) {
        this.code = rent.getCode();
        this.id = rent.getId();
        this.createdAt = rent.getCreatedAt();
        this.status = rent.getStatus();
        this.total = rent.getTotal();
        this.deliveryTax = rent.getDeliveryTax();
        this.deliveryDate = rent.getDeliveryDate();
        this.returnDate = rent.getReturnDate();
        this.costumerId = rent.getCostumerId();
        this.userId = rent.getUserId();
        this.items = rent.getItems().stream().map(RentItemResponse::new).toList();
        this.costumer = new CostumerSummaryResponse(rent.getCostumer());
    }
}
