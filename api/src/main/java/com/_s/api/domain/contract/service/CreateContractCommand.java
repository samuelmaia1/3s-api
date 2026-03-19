package com._s.api.domain.contract.service;

import com._s.api.presentation.dto.ContractRequest;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateContractCommand {
    private String orderId;

    private List<String> clausesIds;

    private String costumerId;

    public CreateContractCommand(ContractRequest request) {
        this.orderId = request.getOrderId();
        this.clausesIds = request.getClausesIds();
        this.costumerId = request.getCostumerId();
    }
}
