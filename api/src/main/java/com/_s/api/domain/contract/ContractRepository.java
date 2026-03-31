package com._s.api.domain.contract;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ContractRepository {
    Contract save(Contract contract);
    Optional<Contract> findById(String id);
    Optional<Contract> findByOrderId(String orderId);
    Optional<Contract> findByCode(String code);
    Page<Contract> findAllByUserId(String userId, Pageable pageable);
    List<Contract> findLastContractsByUser(
            String userId,
            Pageable pageable
    );
    Boolean existsByOrderId(String orderId);
    void delete(Contract contract);
    void deleteByOrderId(String orderId);
}
