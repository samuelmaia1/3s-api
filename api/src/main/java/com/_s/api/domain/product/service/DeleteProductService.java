package com._s.api.domain.product.service;

import com._s.api.domain.product.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteProductService {
    private final ProductRepository repository;

    public DeleteProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public void executeById(String id) {
        repository.delete(id);
    }
}
