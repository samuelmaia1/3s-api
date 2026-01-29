package com._s.api.infra.repositories.adapters;

import com._s.api.domain.order.Order;
import com._s.api.domain.order.OrderRepository;
import com._s.api.infra.mappers.OrderMapper;
import com._s.api.infra.mappers.ProductMapper;
import com._s.api.infra.repositories.OrderJpaRepository;
import com._s.api.infra.repositories.UserJpaRepository;
import com._s.api.infra.repositories.entity.OrderEntity;
import com._s.api.infra.repositories.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class OrderRepositoryAdapter implements OrderRepository {

    private final OrderJpaRepository repository;
    private final UserJpaRepository userRepository;

    public OrderRepositoryAdapter(OrderJpaRepository repository, UserJpaRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Order save(Order order) {
        UserEntity userRef = userRepository.getReferenceById(order.getUserId());

        OrderEntity entity = OrderMapper.toEntity(order, userRef);

        return OrderMapper.toDomain(repository.save(entity));
    }

    public Page<Order> findAllByUserId(String userId, Pageable pageable) {
        return repository.findAllByUserId(userId, pageable).map(OrderMapper::toDomain);
    }
}
