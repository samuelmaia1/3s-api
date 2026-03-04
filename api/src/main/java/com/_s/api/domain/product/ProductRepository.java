package com._s.api.domain.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product, String userId);
    Optional<Product> findById(String id);
    Page<Product> findAllByUserId(String userId, Pageable pageable);
    List<Product> findAllByIdIn(List<String> ids);
    void saveAll(List<Product> products, String userId);
    void decreaseStock(String id, Integer quantity);
    void delete(String id);
}
