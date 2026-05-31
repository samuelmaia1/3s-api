package com._s.api.domain.orderItem;

import com._s.api.domain.orderItem.exception.InsufficientStockException;
import com._s.api.domain.orderItem.exception.InvalidOrderItemQuantityException;
import com._s.api.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemPolicyTest {

    private OrderItemPolicy policy;
    private Product product;

    @BeforeEach
    void setUp() {
        policy = new OrderItemPolicy();
        product = new Product();
        product.setName("Produto Teste");
        product.setStock(10);
        product.setPrice(new BigDecimal("100.00"));
    }

    @Test
    void devePassarValidacaoComQuantidadeValida() {
        assertDoesNotThrow(() -> policy.validateProductQuantity(5, product));
    }

    @Test
    void devePassarValidacaoComQuantidadeIgualAoEstoque() {
        assertDoesNotThrow(() -> policy.validateProductQuantity(10, product));
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeEZero() {
        assertThrows(InvalidOrderItemQuantityException.class,
                () -> policy.validateProductQuantity(0, product));
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeENegativa() {
        assertThrows(InvalidOrderItemQuantityException.class,
                () -> policy.validateProductQuantity(-1, product));
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeExcedeEstoque() {
        assertThrows(InsufficientStockException.class,
                () -> policy.validateProductQuantity(11, product));
    }

    @Test
    void deveLancarExcecaoComMensagemCorretaParaQuantidadeInvalida() {
        InvalidOrderItemQuantityException ex = assertThrows(
                InvalidOrderItemQuantityException.class,
                () -> policy.validateProductQuantity(0, product)
        );
        assertNotNull(ex.getMessage());
    }

    @Test
    void deveLancarExcecaoComMensagemCorretaParaEstoqueInsuficiente() {
        InsufficientStockException ex = assertThrows(
                InsufficientStockException.class,
                () -> policy.validateProductQuantity(100, product)
        );
        assertNotNull(ex.getMessage());
    }
}
