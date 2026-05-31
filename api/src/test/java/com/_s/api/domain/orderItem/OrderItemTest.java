package com._s.api.domain.orderItem;

import com._s.api.domain.product.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    private Product buildProduct(Integer stock) {
        Product p = new Product();
        p.setName("Produto Teste");
        p.setStock(stock);
        p.setPrice(new BigDecimal("50.00"));
        return p;
    }

    @Test
    void deveCalcularSubTotalCorretamente() {
        Product product = buildProduct(10);
        OrderItem item = new OrderItem(product, new BigDecimal("50.00"), 3);
        item.calculateSubTotal();

        assertEquals(new BigDecimal("150.00"), item.getSubTotal());
    }

    @Test
    void deveCalcularSubTotalComQuantidadeUm() {
        Product product = buildProduct(5);
        OrderItem item = new OrderItem(product, new BigDecimal("99.90"), 1);
        item.calculateSubTotal();

        assertEquals(new BigDecimal("99.90"), item.getSubTotal());
    }

    @Test
    void deveRetornarZeroQuandoUnitValueENulo() {
        Product product = buildProduct(5);
        OrderItem item = new OrderItem(product, null, 3);
        item.calculateSubTotal();

        assertEquals(BigDecimal.ZERO, item.getSubTotal());
    }

    @Test
    void deveRetornarZeroQuandoQuantidadeENula() {
        Product product = buildProduct(5);
        OrderItem item = new OrderItem(product, new BigDecimal("50.00"), null);
        item.calculateSubTotal();

        assertEquals(BigDecimal.ZERO, item.getSubTotal());
    }

    @Test
    void deveMontarItemComTodosOsCampos() {
        Product product = buildProduct(10);
        OrderItem item = OrderItem.mount("id-1", product, new BigDecimal("50.00"), new BigDecimal("100.00"), 2, "order-1");

        assertEquals("id-1", item.getId());
        assertEquals(product, item.getProduct());
        assertEquals(new BigDecimal("50.00"), item.getUnitValue());
        assertEquals(new BigDecimal("100.00"), item.getSubTotal());
        assertEquals(2, item.getQuantity());
        assertEquals("order-1", item.getOrderId());
    }
}
