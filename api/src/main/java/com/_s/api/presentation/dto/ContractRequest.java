package com._s.api.presentation.dto;

import com._s.api.domain.order.Order;
import com._s.api.infra.repositories.entity.OrderEntity;
import lombok.Data;

import java.util.List;

@Data
public class ContractRequest {
    private OrderEntity order;
    private List<ClauseRequest> clauses;
}
