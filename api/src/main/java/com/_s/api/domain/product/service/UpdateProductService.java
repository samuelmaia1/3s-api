package com._s.api.domain.product.service;

import com._s.api.domain.product.Product;
import com._s.api.domain.product.ProductRepository;
import com._s.api.domain.product.exception.ProductNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateProductService {
    private final ProductRepository repository;

    public UpdateProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Product execute(UpdateProductCommand command, String userId) {
        Optional<Product> optionalProduct = repository.findById(command.getId());

        if (optionalProduct.isEmpty())
            throw new ProductNotFoundException("Produto não encontrado.");

        Product product = optionalProduct.get();

        product.update(command);

        return repository.save(product, userId);
    }

}
