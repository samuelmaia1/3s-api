package com._s.api.domain.orderItem;

import com._s.api.domain.orderItem.exception.InsufficientStockException;
import com._s.api.domain.orderItem.exception.InvalidOrderItemQuantityException;
import com._s.api.domain.product.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
public class OrderItemPolicy {
    public void validateProductQuantity(Integer quantity, Product product){
        if (quantity <= 0)
            throw new InvalidOrderItemQuantityException("A quantidade de itens deve ser um valor maior que zero.");

        if (quantity > product.getStock())
            throw new InsufficientStockException("A quantidade de itens não deve exceder o estoque");

    }
}
