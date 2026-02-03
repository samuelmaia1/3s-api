package com._s.api.domain.costumer.service;

import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.costumer.CostumerNotFoundException;
import com._s.api.domain.costumer.CostumerRepository;
import com._s.api.domain.user.UserRepository;
import com._s.api.domain.user.exception.UserNotFoundException;
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
}
