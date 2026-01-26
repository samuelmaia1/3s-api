package com._s.api.infra.mappers;

import com._s.api.domain.product.Product;
import com._s.api.infra.repositories.entity.ProductEntity;
import com._s.api.infra.repositories.entity.UserEntity;

public class ProductMapper{
    private ProductMapper() {}

    public static Product toDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }

        Product product = new Product();
        product.setId(entity.getId());
        product.setDescription(entity.getDescription());
        product.setName(entity.getName());
        product.setPrice(entity.getPrice());
        product.setStock(entity.getStock());
        product.setImageUri(entity.getImageUri());

        return product;
    }
    public static ProductEntity toEntity(Product product, UserEntity user) {
        if (product == null) {
            return null;
        }

        ProductEntity entity = new ProductEntity();
        entity.setDescription(product.getDescription());
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());
        entity.setStock(product.getStock());
        entity.setImageUri(product.getImageUri());
        entity.setUser(user);

        return entity;
    }
}
