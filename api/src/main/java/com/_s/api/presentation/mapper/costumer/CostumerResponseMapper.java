package com._s.api.presentation.mapper.costumer;

import com._s.api.domain.costumer.Costumer;
import com._s.api.presentation.response.CostumerResponse;
import com._s.api.presentation.response.CostumerResponseWithOrders;

public class CostumerResponseMapper {
    public static CostumerResponse toResponse(Costumer costumer) {
        return new CostumerResponse(costumer);
    }

    public static CostumerResponseWithOrders toResponseWithOrders(Costumer costumer) {
        return new CostumerResponseWithOrders(costumer);
    }
}
