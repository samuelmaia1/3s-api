package com._s.api.domain.order.service;

import com._s.api.domain.order.Order;
import com._s.api.domain.order.OrderRepository;
import com._s.api.domain.order.OrderStatus;
import com._s.api.domain.order.exception.OrderNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrderService {

    private final OrderRepository repository;

    public UpdateOrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order updateStatus(String id, OrderStatus status) {
        repository.updateStatus(id, status);

        return repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado."));
    }
}
