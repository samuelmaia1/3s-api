package com._s.api.infra.mappers;

import com._s.api.domain.contract.Contract;
import com._s.api.domain.clause.Clause;
import com._s.api.infra.repositories.entity.ContractEntity;
import com._s.api.infra.repositories.entity.ClauseEntity;
import com._s.api.infra.repositories.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class ContractMapper {

    private ContractMapper() {}

    public static Contract toDomain(ContractEntity entity) {
        if (entity == null) {
            return null;
        }

        List<Clause> clauses = entity.getClauses()
                .stream()
                .map(ClauseMapper::toDomain)
                .toList();

        return Contract.mount(
                entity.getId(),
                entity.getCode(),
                entity.getUser().getId(),
                entity.getCostumerId(),
                entity.getReferenceId(),
                entity.getReferenceType(),
                entity.getStatus(),
                entity.getCreatedAt(),
                clauses
        );
    }

    public static ContractEntity toEntity(Contract contract) {
        return toEntity(contract, null);
    }

    public static ContractEntity toEntity(Contract contract, UserEntity userEntity) {
        if (contract == null) {
            return null;
        }

        ContractEntity entity = new ContractEntity();

        entity.setId(contract.getId());
        entity.setCode(contract.getCode());
        entity.setUser(userEntity);
        entity.setCostumerId(contract.getCostumerId());
        entity.setReferenceId(contract.getReferenceId());
        entity.setReferenceType(contract.getReferenceType());
        entity.setStatus(contract.getStatus());
        entity.setCreatedAt(contract.getCreatedAt());

        entity.setClauses(new ArrayList<>());

        for (Clause clause : contract.getClauses()) {
            ClauseEntity clauseEntity = ClauseMapper.toEntity(clause);
            entity.getClauses().add(clauseEntity);
        }

        return entity;
    }
}
