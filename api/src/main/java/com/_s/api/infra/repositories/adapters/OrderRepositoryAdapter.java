package com._s.api.infra.repositories.adapters;

import com._s.api.domain.order.Order;
import com._s.api.domain.order.OrderFilter;
import com._s.api.domain.order.OrderRepository;
import com._s.api.domain.order.OrderStatus;
import com._s.api.domain.order.exception.OrderNotFoundException;
import com._s.api.infra.mappers.OrderMapper;
import com._s.api.infra.repositories.CostumerJpaRepository;
import com._s.api.infra.repositories.OrderJpaRepository;
import com._s.api.infra.repositories.UserJpaRepository;
import com._s.api.infra.repositories.specification.OrderSpecifications;
import com._s.api.infra.repositories.entity.CostumerEntity;
import com._s.api.infra.repositories.entity.OrderEntity;
import com._s.api.infra.repositories.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderRepositoryAdapter implements OrderRepository {

    private final OrderJpaRepository repository;
    private final UserJpaRepository userRepository;
    private final CostumerJpaRepository costumerRepository;

    public OrderRepositoryAdapter(OrderJpaRepository repository, UserJpaRepository userRepository, CostumerJpaRepository costumerRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.costumerRepository = costumerRepository;
    }

    public Order save(Order order) {
        UserEntity userRef = userRepository.getReferenceById(order.getUserId());
        CostumerEntity costumerRef = costumerRepository.getReferenceById(order.getCostumerId());

        OrderEntity entity = OrderMapper.toEntity(order, userRef, costumerRef);

        return OrderMapper.toDomain(repository.save(entity));
    }

    public Page<Order> findAllByUserId(String userId, Pageable pageable) {
        return repository.findAllByUserId(userId, pageable).map(OrderMapper::toDomain);
    }

    @Override
    public Page<Order> findAllByUserId(String userId, OrderFilter filter, Pageable pageable) {
        return repository.findAll(OrderSpecifications.withFilters(userId, filter), pageable).map(OrderMapper::toDomain);
    }

    @Override
    public Page<Order> findAllByCostumerId(String costumerId, Pageable pageable) {
        return repository.findAllByCostumerId(costumerId, pageable).map(OrderMapper::toDomain);
    }

    @Override
    public Optional<Order> findById(String id) {
        return repository.findById(id).map(OrderMapper::toDomain);
    }

    @Override
    @Transactional
    public void updateStatus(String id, OrderStatus status) {
        Optional<OrderEntity> optionalOrderEntity = repository.findById(id);

        if (optionalOrderEntity.isEmpty())
            throw new OrderNotFoundException("Pedido não encontrado.");

        OrderEntity entity = optionalOrderEntity.get();

        entity.setStatus(status);
    }

    @Override
    public List<Order> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId, pageable).stream().map(OrderMapper::toDomain).toList();
    }
}
