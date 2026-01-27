package com._s.api.presentation.controllers;

import com._s.api.domain.product.service.GetProductsService;
import com._s.api.presentation.mapper.product.ProductResponseMapper;
import com._s.api.presentation.response.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/products")
@RestController
public class ProductController {
    private final GetProductsService getProductsService;

    public ProductController(GetProductsService getProductsService) {
        this.getProductsService = getProductsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable String id) {
        return ResponseEntity
                .ok(ProductResponseMapper
                        .toResponse(getProductsService.executeById(id))
                );
    }
}
