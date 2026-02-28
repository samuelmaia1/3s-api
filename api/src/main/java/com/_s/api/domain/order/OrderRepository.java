package com._s.api.domain.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Page<Order> findAllByUserId(String userId, Pageable pageable);
    Page<Order> findAllByCostumerId(String costumerId, Pageable pageable);
    Optional<Order> findById(String id);
    void updateStatus(String id, OrderStatus status);
    List<Order> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);
}
