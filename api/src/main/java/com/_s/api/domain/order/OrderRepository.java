package com._s.api.domain.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepository {
    Order save(Order order);
    Page<Order> findAllByUserId(String userId, Pageable pageable);
}
