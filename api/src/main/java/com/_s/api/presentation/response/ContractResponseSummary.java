package com._s.api.presentation.response;

import com._s.api.domain.clause.Clause;
import com._s.api.domain.contract.Contract;
import com._s.api.domain.contract.ContractReferenceType;
import com._s.api.domain.contract.ContractStatus;
import com._s.api.domain.costumer.Costumer;
import com._s.api.presentation.mapper.costumer.CostumerResponseMapper;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ContractResponseSummary {
    private String id;
    private String code;
    private String userId;
    private String costumerId;
    private String referenceId;
    private ContractReferenceType referenceType;
    private ContractStatus status;
    private LocalDateTime createdAt;

    private List<Clause> clauses = new ArrayList<>();

    private CostumerResponse costumer;

    public ContractResponseSummary(Contract contract, Costumer costumer) {
        this.id = contract.getId();
        this.code = contract.getCode();
        this.userId = contract.getUserId();
        this.costumerId = contract.getCostumerId();
        this.referenceId = contract.getReferenceId();
        this.referenceType = contract.getReferenceType();
        this.status = contract.getStatus();
        this.createdAt = contract.getCreatedAt();
        this.clauses = contract.getClauses();
        this.costumer = CostumerResponseMapper.toResponse(costumer);
    }
}
