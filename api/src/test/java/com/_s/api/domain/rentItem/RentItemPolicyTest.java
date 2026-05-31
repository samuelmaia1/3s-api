package com._s.api.domain.rentItem;

import com._s.api.domain.orderItem.exception.InsufficientStockException;
import com._s.api.domain.orderItem.exception.InvalidOrderItemQuantityException;
import com._s.api.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class RentItemPolicyTest {

    private RentItemPolicy policy;
    private Product product;

    @BeforeEach
    void setUp() {
        policy = new RentItemPolicy();
        product = new Product();
        product.setName("Produto Aluguel");
        product.setStock(15);
        product.setPrice(new BigDecimal("200.00"));
    }

    @Test
    void devePassarValidacaoComQuantidadeValida() {
        assertDoesNotThrow(() -> policy.validateProductQuantity(5, product));
    }

    @Test
    void devePassarValidacaoComQuantidadeIgualAoEstoque() {
        assertDoesNotThrow(() -> policy.validateProductQuantity(15, product));
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeEZero() {
        assertThrows(InvalidOrderItemQuantityException.class,
                () -> policy.validateProductQuantity(0, product));
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeENegativa() {
        assertThrows(InvalidOrderItemQuantityException.class,
                () -> policy.validateProductQuantity(-5, product));
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeExcedeEstoque() {
        assertThrows(InsufficientStockException.class,
                () -> policy.validateProductQuantity(16, product));
    }
}
