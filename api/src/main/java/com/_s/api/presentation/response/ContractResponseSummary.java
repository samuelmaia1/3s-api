package com._s.api.presentation.response;

import com._s.api.domain.clause.Clause;
import com._s.api.domain.contract.Contract;
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
    private String costumerId;
    private String orderId;
    private ContractStatus status;
    private LocalDateTime createdAt;

    private List<Clause> clauses = new ArrayList<>();

    private CostumerResponse costumer;

    public ContractResponseSummary(Contract contract, Costumer costumer) {
        this.id = contract.getId();
        this.code = contract.getCode();
        this.costumerId = contract.getCostumerId();
        this.orderId = contract.getOrderId();
        this.status = contract.getStatus();
        this.createdAt = contract.getCreatedAt();
        this.clauses = contract.getClauses();
        this.costumer = CostumerResponseMapper.toResponse(costumer);
    }
}
