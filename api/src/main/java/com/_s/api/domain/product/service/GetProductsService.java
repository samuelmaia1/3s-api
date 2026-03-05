package com._s.api.domain.product.service;

import com._s.api.domain.exception.EntityNotFoundException;
import com._s.api.domain.product.Product;
import com._s.api.domain.product.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetProductsService {
    private final ProductRepository repository;

    public GetProductsService(ProductRepository repository) {
        this.repository = repository;
    }

    public Page<Product> executeByUserId(String userId, String name, Pageable pageable) {

        if (name != null && !name.isBlank()) {
            return repository.findByUserIdAndNameContainingIgnoreCase(userId, name, pageable);
        }

        return repository.findAllByUserId(userId, pageable);
    }

    public Product executeById(String id) {
        Optional<Product> optionalProduct = repository.findById(id);

        if (optionalProduct.isEmpty())
            throw new EntityNotFoundException("Produto não encontrado");

        return optionalProduct.get();
    }
}
