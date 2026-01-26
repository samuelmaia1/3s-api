package com._s.api.presentation.mapper.product;

import com._s.api.domain.product.Product;
import com._s.api.presentation.response.ProductResponse;

public class ProductResponseMapper {
    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(product);
    }
}
