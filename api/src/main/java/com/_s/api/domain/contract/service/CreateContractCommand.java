package com._s.api.domain.contract.service;

import com._s.api.domain.contract.ContractReferenceType;
import com._s.api.presentation.dto.ContractRequest;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateContractCommand {
    private String referenceId;

    private ContractReferenceType referenceType;

    private List<String> clausesIds;

    private String costumerId;

    public CreateContractCommand(ContractRequest request) {
        this.referenceId = request.getReferenceId();
        this.referenceType = request.getReferenceType();
        this.clausesIds = request.getClausesIds();
        this.costumerId = request.getCostumerId();
    }
}
