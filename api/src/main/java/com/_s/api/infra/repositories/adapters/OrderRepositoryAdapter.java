package com._s.api.infra.repositories.adapters;

import com._s.api.domain.costumer.CostumerRepository;
import com._s.api.domain.order.Order;
import com._s.api.domain.order.OrderRepository;
import com._s.api.infra.mappers.OrderMapper;
import com._s.api.infra.mappers.ProductMapper;
import com._s.api.infra.repositories.CostumerJpaRepository;
import com._s.api.infra.repositories.OrderJpaRepository;
import com._s.api.infra.repositories.UserJpaRepository;
import com._s.api.infra.repositories.entity.CostumerEntity;
import com._s.api.infra.repositories.entity.OrderEntity;
import com._s.api.infra.repositories.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

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
}
