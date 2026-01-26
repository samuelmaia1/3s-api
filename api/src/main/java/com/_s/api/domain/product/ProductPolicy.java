package com._s.api.domain.product;

import com._s.api.domain.product.service.CreateProductCommand;
import org.springframework.stereotype.Component;

@Component
public class ProductPolicy {

    public void validateFields(CreateProductCommand command) {

    }

}
