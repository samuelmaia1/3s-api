package com._s.api.presentation.response;

import com._s.api.domain.contract.Contract;
import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.order.Order;
import com._s.api.presentation.mapper.order.OrderResponseMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ContractResponseWithOrder extends ContractResponseSummary {

    private final OrderResponse order;

    public ContractResponseWithOrder(Contract contract, Costumer costumer, Order order) {
        super(contract, costumer);
        this.order = OrderResponseMapper.toResponse(order);
    }
}
