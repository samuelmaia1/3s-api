package com._s.api.infra.repositories.adapters;

import com._s.api.domain.contract.Contract;
import com._s.api.domain.contract.ContractReferenceType;
import com._s.api.domain.contract.ContractRepository;
import com._s.api.infra.mappers.ContractMapper;
import com._s.api.infra.repositories.ContractJpaRepository;
import com._s.api.infra.repositories.UserJpaRepository;
import com._s.api.infra.repositories.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContractRepositoryAdapter implements ContractRepository {

    private final ContractJpaRepository repository;
    private final UserJpaRepository userRepository;

    @Override
    public Contract save(Contract contract) {
        UserEntity userRef = userRepository.getReferenceById(contract.getUserId());

        return ContractMapper.toDomain(
                repository.save(ContractMapper.toEntity(contract, userRef))
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
    public Page<Contract> findAllByUserId(String userId, Pageable pageable) {
        return repository.findAllByUserIdOrderByCreatedAtDesc(userId, pageable).map(ContractMapper::toDomain);
    }

    @Override
    public List<Contract> findLastContractsByUser(String userId, Pageable pageable) {
        return repository.findLastContractsByUser(userId, pageable).stream().map(ContractMapper::toDomain).toList();
    }

    @Override
    public Boolean existsByReferenceIdAndReferenceType(String referenceId, ContractReferenceType referenceType) {
        return repository.existsByReferenceIdAndReferenceType(referenceId, referenceType);
    }

    @Override
    public void delete(Contract contract) {
        repository.deleteById(contract.getId());
    }

    @Override
    public Optional<Contract> findByReferenceIdAndReferenceType(String referenceId, ContractReferenceType referenceType) {
        return repository.findByReferenceIdAndReferenceType(referenceId, referenceType).map(ContractMapper::toDomain);
    }

    @Override
    public void deleteByReferenceIdAndReferenceType(String referenceId, ContractReferenceType referenceType) {
        repository.deleteByReferenceIdAndReferenceType(referenceId, referenceType);
    }
}
