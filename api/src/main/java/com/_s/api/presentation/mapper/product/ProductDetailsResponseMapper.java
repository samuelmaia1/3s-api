package com._s.api.presentation.mapper.product;

import com._s.api.domain.product.service.GetProductDetailsService;
import com._s.api.presentation.response.ProductDetailsResponse;

public class ProductDetailsResponseMapper {
    public static ProductDetailsResponse toResponse(GetProductDetailsService.ProductDetailsData data) {
        return new ProductDetailsResponse(data);
    }
}
