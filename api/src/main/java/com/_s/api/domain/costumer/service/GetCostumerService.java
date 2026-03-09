package com._s.api.domain.costumer.service;

import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.costumer.CostumerNotFoundException;
import com._s.api.domain.costumer.CostumerRepository;
import com._s.api.domain.order.Order;
import com._s.api.domain.user.UserRepository;
import com._s.api.domain.user.exception.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetCostumerService {
    private final CostumerRepository repository;

    public GetCostumerService(CostumerRepository repository) {
        this.repository = repository;
    }

    public Costumer execute(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new CostumerNotFoundException());
    }
    public Page<Costumer> executeByUserId(String userId, String name, Pageable pageable) {

        if (name != null && !name.isBlank()) {
            return repository.findByUserIdAndNameContainingIgnoreCase(userId, name, pageable);
        }

        return repository.findAllByUserId(userId, pageable);
    }

}
