package com._s.api.presentation.controllers;

import com._s.api.domain.product.Product;
import com._s.api.domain.product.service.DeleteProductService;
import com._s.api.domain.product.service.GetProductsService;
import com._s.api.domain.product.service.UpdateProductCommand;
import com._s.api.domain.product.service.UpdateProductService;
import com._s.api.infra.security.AuthenticatedUser;
import com._s.api.presentation.dto.UpdateProductRequest;
import com._s.api.presentation.mapper.product.ProductRequestMapper;
import com._s.api.presentation.mapper.product.ProductResponseMapper;
import com._s.api.presentation.response.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/products")
@RestController
public class ProductController {
    private final GetProductsService getProductsService;
    private final DeleteProductService deleteProductService;
    private final UpdateProductService updateProductService;

    public ProductController(
            GetProductsService getProductsService,
            DeleteProductService deleteProductService,
            UpdateProductService updateProductService
    ) {
        this.getProductsService = getProductsService;
        this.deleteProductService = deleteProductService;
        this.updateProductService = updateProductService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable String id) {
        return ResponseEntity
                .ok(ProductResponseMapper
                        .toResponse(getProductsService.executeById(id))
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        getProductsService.executeById(id);
        deleteProductService.executeById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(
            @PathVariable String id,
            @RequestBody @Valid UpdateProductRequest request,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        getProductsService.executeById(id);

        UpdateProductCommand command = ProductRequestMapper.toUpdateCommand(request, id);

        return ResponseEntity.status(HttpStatus.OK).body(updateProductService.execute(command, user.id()));
    }
}
