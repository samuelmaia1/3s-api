package com._s.api.domain.contract;

import com._s.api.infra.repositories.entity.ContractEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContractRepository {
    Contract save(Contract contract);
    Optional<Contract> findById(String id);
    Optional<Contract> findByCode(String code);
    List<Contract> findLastContractsByUser(
            String userId,
            Pageable pageable
    );
}
