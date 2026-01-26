package com._s.api.domain.product;

import com._s.api.domain.user.User;

import java.util.Optional;

public interface ProductRepository {
    Product save(Product product, String userId);
    Optional<Product> findById(String id);
}
