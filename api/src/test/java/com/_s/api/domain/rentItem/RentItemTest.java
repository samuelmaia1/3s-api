package com._s.api.domain.rentItem;

import com._s.api.domain.product.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class RentItemTest {

    private Product buildProduct() {
        Product p = new Product();
        p.setName("Produto Aluguel");
        p.setStock(20);
        p.setPrice(new BigDecimal("80.00"));
        return p;
    }

    @Test
    void deveCalcularSubTotalCorretamente() {
        Product product = buildProduct();
        RentItem item = new RentItem(product, new BigDecimal("80.00"), 4);
        item.calculateSubTotal();

        assertEquals(new BigDecimal("320.00"), item.getSubTotal());
    }

    @Test
    void deveCalcularSubTotalComQuantidadeUm() {
        Product product = buildProduct();
        RentItem item = new RentItem(product, new BigDecimal("250.00"), 1);
        item.calculateSubTotal();

        assertEquals(new BigDecimal("250.00"), item.getSubTotal());
    }

    @Test
    void deveRetornarZeroQuandoUnitValueENulo() {
        Product product = buildProduct();
        RentItem item = new RentItem(product, null, 3);
        item.calculateSubTotal();

        assertEquals(BigDecimal.ZERO, item.getSubTotal());
    }

    @Test
    void deveRetornarZeroQuandoQuantidadeENula() {
        Product product = buildProduct();
        RentItem item = new RentItem(product, new BigDecimal("80.00"), null);
        item.calculateSubTotal();

        assertEquals(BigDecimal.ZERO, item.getSubTotal());
    }

    @Test
    void deveMontarItemComTodosOsCampos() {
        Product product = buildProduct();
        RentItem item = RentItem.mount("id-rent-1", product, new BigDecimal("80.00"), new BigDecimal("160.00"), 2, "rent-1");

        assertEquals("id-rent-1", item.getId());
        assertEquals(product, item.getProduct());
        assertEquals(new BigDecimal("80.00"), item.getUnitValue());
        assertEquals(new BigDecimal("160.00"), item.getSubTotal());
        assertEquals(2, item.getQuantity());
        assertEquals("rent-1", item.getRentId());
    }
}
