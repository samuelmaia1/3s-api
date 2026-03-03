package com._s.api.domain.product.service;

import com._s.api.domain.product.Product;
import com._s.api.domain.product.ProductPolicy;
import com._s.api.domain.product.ProductRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class CreateProductService {
    private final ProductRepository repository;
    private final ProductPolicy productPolicy;
    private final SimpMessagingTemplate messagingTemplate;

    public CreateProductService(
            ProductRepository repository,
            ProductPolicy productPolicy,
            SimpMessagingTemplate messagingTemplate
    ) {
        this.repository = repository;
        this.productPolicy = productPolicy;
        this.messagingTemplate = messagingTemplate;
    }

    public Product execute(CreateProductCommand command, String userId) {
        productPolicy.validateFields(command);

        Product createdProduct = repository.save(new Product(command), userId);

        messagingTemplate.convertAndSend("/topic/products", createdProduct);

        return createdProduct;
    }
}
