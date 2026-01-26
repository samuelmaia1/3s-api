package com._s.api.domain.product.service;

import com._s.api.domain.product.Product;
import com._s.api.domain.product.ProductPolicy;
import com._s.api.domain.product.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateProductService {
    private final ProductRepository repository;
    private final ProductPolicy productPolicy;

    public CreateProductService(ProductRepository repository, ProductPolicy productPolicy) {
        this.repository = repository;
        this.productPolicy = productPolicy;
    }

    public Product execute(CreateProductCommand command, String userId) {
        productPolicy.validateFields(command);

        return repository.save(new Product(command), userId);
    }
}
