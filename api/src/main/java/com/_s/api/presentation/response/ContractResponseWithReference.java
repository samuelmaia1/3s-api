package com._s.api.presentation.response;

import com._s.api.domain.contract.Contract;
import com._s.api.domain.contract.ContractReferenceType;
import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.order.Order;
import com._s.api.domain.rent.Rent;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ContractResponseWithReference extends ContractResponseSummary {

    private final Object reference;

    public ContractResponseWithReference(Contract contract, Costumer costumer, Object reference) {
        super(contract, costumer);
        this.reference = mapReference(contract.getReferenceType(), reference);
    }

    private Object mapReference(ContractReferenceType referenceType, Object reference) {
        if (referenceType == ContractReferenceType.ORDER && reference instanceof Order order) {
            return new OrderResponse(order);
        }

        if (referenceType == ContractReferenceType.RENT && reference instanceof Rent rent) {
            return new RentResponse(rent);
        }

        return null;
    }
}
