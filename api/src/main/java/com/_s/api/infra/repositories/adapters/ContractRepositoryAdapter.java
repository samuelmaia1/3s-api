package com._s.api.infra.repositories.adapters;

import com._s.api.domain.contract.Contract;
import com._s.api.domain.contract.ContractRepository;
import com._s.api.infra.mappers.ContractMapper;
import com._s.api.infra.repositories.ContractJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContractRepositoryAdapter implements ContractRepository {

    private final ContractJpaRepository repository;

    @Override
    public Contract save(Contract contract) {
        return ContractMapper.toDomain(
                repository.save(ContractMapper.toEntity(contract))
        );
    }

    @Override
    public Optional<Contract> findById(String id) {
        return repository.findById(id).map(ContractMapper::toDomain);
    }

    @Override
    public Optional<Contract> findByCode(String code) {
        return repository.findByCode(code).map(ContractMapper::toDomain);
    }

    @Override
    public List<Contract> findLastContractsByUser(String userId, Pageable pageable) {
        return repository.findLastContractsByUser(userId, pageable).stream().map(ContractMapper::toDomain).toList();
    }

    @Override
    public Boolean existsByOrderId(String orderId) {
        return repository.existsByOrderId(orderId);
    }

    @Override
    public void delete(Contract contract) {
        repository.delete(ContractMapper.toEntity(contract));
    }

    @Override
    public Optional<Contract> findByOrderId(String orderId) {
        return repository.findByOrderId(orderId).map(ContractMapper::toDomain);
    }
}

