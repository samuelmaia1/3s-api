package com._s.api.domain.product.service;

import com._s.api.domain.product.Product;
import com._s.api.domain.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetProductsService {
    private final ProductRepository repository;

    public GetProductsService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getByUserId(String userId) {
        return repository.findAllByUserId(userId);
    }
}
