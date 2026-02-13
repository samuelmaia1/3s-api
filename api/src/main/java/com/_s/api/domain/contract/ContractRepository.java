package com._s.api.domain.contract;

import java.util.Optional;

public interface ContractRepository {
    Contract save(Contract contract);
    Optional<Contract> findById(String id);
    Optional<Contract> findByCode(String code);
}
