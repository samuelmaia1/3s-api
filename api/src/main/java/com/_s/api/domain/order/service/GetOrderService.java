package com._s.api.domain.order.service;

import com._s.api.domain.order.Order;
import com._s.api.domain.order.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetOrderService {

    private final OrderRepository repository;

    public GetOrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Page<Order> executeByUserId(String id, Pageable pageable) {
        return repository.findAllByUserId(id, pageable);
    }
}
