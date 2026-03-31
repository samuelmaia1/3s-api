package com._s.api.domain.costumer.service;

import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.costumer.CostumerNotFoundException;
import com._s.api.domain.costumer.CostumerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetCostumerService {
    private final CostumerRepository repository;

    public GetCostumerService(CostumerRepository repository) {
        this.repository = repository;
    }

    public Costumer execute(String id) {
        Costumer costumer = repository.findById(id)
                .orElseThrow(CostumerNotFoundException::new);

        return costumer;
    }
    public Page<Costumer> executeByUserId(String userId, String name, Pageable pageable) {

        if (name != null && !name.isBlank()) {
            return repository.findByUserIdAndNameContainingIgnoreCase(userId, name, pageable);
        }

        return repository.findAllByUserId(userId, pageable);
    }

    public List<Costumer> executeByIds(List<String> ids) {
        return repository.findByIdIn(ids);
    }

}
