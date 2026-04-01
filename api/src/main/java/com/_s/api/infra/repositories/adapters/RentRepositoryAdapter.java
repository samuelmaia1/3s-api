package com._s.api.infra.repositories.adapters;

import com._s.api.domain.rent.Rent;
import com._s.api.domain.rent.RentRepository;
import com._s.api.domain.rent.RentStatus;
import com._s.api.domain.rent.exception.RentNotFoundException;
import com._s.api.infra.mappers.RentMapper;
import com._s.api.infra.repositories.CostumerJpaRepository;
import com._s.api.infra.repositories.RentJpaRepository;
import com._s.api.infra.repositories.UserJpaRepository;
import com._s.api.infra.repositories.entity.CostumerEntity;
import com._s.api.infra.repositories.entity.RentEntity;
import com._s.api.infra.repositories.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class RentRepositoryAdapter implements RentRepository {

    private final RentJpaRepository repository;
    private final UserJpaRepository userRepository;
    private final CostumerJpaRepository costumerRepository;

    public RentRepositoryAdapter(
            RentJpaRepository repository,
            UserJpaRepository userRepository,
            CostumerJpaRepository costumerRepository
    ) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.costumerRepository = costumerRepository;
    }

    @Override
    public Rent save(Rent rent) {
        UserEntity userRef = userRepository.getReferenceById(rent.getUserId());
        CostumerEntity costumerRef = costumerRepository.getReferenceById(rent.getCostumerId());

        RentEntity entity = RentMapper.toEntity(rent, userRef, costumerRef);

        return RentMapper.toDomain(repository.save(entity));
    }

    @Override
    public Page<Rent> findAllByUserId(String userId, Pageable pageable) {
        return repository.findAllByUserId(userId, pageable).map(RentMapper::toDomain);
    }

    @Override
    public Page<Rent> findAllByCostumerId(String costumerId, Pageable pageable) {
        return repository.findAllByCostumerId(costumerId, pageable).map(RentMapper::toDomain);
    }

    @Override
    public Optional<Rent> findById(String id) {
        return repository.findById(id).map(RentMapper::toDomain);
    }

    @Override
    @Transactional
    public void updateStatus(String id, RentStatus status) {
        Optional<RentEntity> optionalRentEntity = repository.findById(id);

        if (optionalRentEntity.isEmpty()) {
            throw new RentNotFoundException("Aluguel não encontrado.");
        }

        RentEntity entity = optionalRentEntity.get();
        entity.setStatus(status);
    }

    @Override
    public List<Rent> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .stream()
                .map(RentMapper::toDomain)
                .toList();
    }

    @Override
    public Integer sumReservedQuantityByProductAndPeriod(
            String productId,
            LocalDateTime deliveryDate,
            LocalDateTime returnDate,
            List<RentStatus> statuses
    ) {
        return repository.sumReservedQuantityByProductAndPeriod(productId, deliveryDate, returnDate, statuses);
    }

    @Override
    @Transactional
    public int updateStatusToAguardandoEntrega(
            LocalDateTime start,
            LocalDateTime end,
            List<RentStatus> currentStatuses
    ) {
        return repository.updateStatusToAguardandoEntrega(start, end, currentStatuses);
    }

    @Override
    @Transactional
    public int updateStatusToDevolucaoAtrasada(
            LocalDateTime start,
            LocalDateTime end,
            RentStatus currentStatus
    ) {
        return repository.updateStatusToDevolucaoAtrasada(start, end, currentStatus);
    }
}
