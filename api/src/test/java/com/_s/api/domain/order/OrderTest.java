package com._s.api.domain.order;

import com._s.api.domain.orderItem.OrderItem;
import com._s.api.domain.product.Product;
import com._s.api.domain.shared.Address;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Product buildProduct() {
        Product p = new Product();
        p.setName("Produto");
        p.setStock(50);
        p.setPrice(new BigDecimal("100.00"));
        return p;
    }

    private Address buildAddress() {
        return Address.mount("30130-010", "Rua A", "Centro", "BH", "1");
    }

    @Test
    void deveCalcularTotalComUmItem() {
        Product product = buildProduct();
        OrderItem item = OrderItem.mount("i1", product, new BigDecimal("100.00"), new BigDecimal("100.00"), 1, null);

        Order order = new Order("user-1", "cost-1", OrderStatus.REALIZADO,
                List.of(item), buildAddress(), LocalDateTime.now(), LocalDateTime.now().plusDays(5));
        order.calculateTotal();

        assertEquals(new BigDecimal("100.00"), order.getTotal());
    }

    @Test
    void deveCalcularTotalComMultiplosItens() {
        Product product = buildProduct();
        OrderItem item1 = OrderItem.mount("i1", product, new BigDecimal("100.00"), new BigDecimal("200.00"), 2, null);
        OrderItem item2 = OrderItem.mount("i2", product, new BigDecimal("50.00"),  new BigDecimal("150.00"), 3, null);

        Order order = new Order("user-1", "cost-1", OrderStatus.REALIZADO,
                List.of(item1, item2), buildAddress(), LocalDateTime.now(), LocalDateTime.now().plusDays(5));
        order.calculateTotal();

        assertEquals(new BigDecimal("350.00"), order.getTotal());
    }

    @Test
    void deveCalcularTotalComoZeroComListaVazia() {
        Order order = new Order("user-1", "cost-1", OrderStatus.REALIZADO,
                new ArrayList<>(), buildAddress(), LocalDateTime.now(), LocalDateTime.now().plusDays(5));
        order.calculateTotal();

        assertEquals(BigDecimal.ZERO, order.getTotal());
    }

    @Test
    void deveIgnorarItensComSubTotalNulo() {
        Product product = buildProduct();
        OrderItem itemValido   = OrderItem.mount("i1", product, new BigDecimal("100.00"), new BigDecimal("100.00"), 1, null);
        OrderItem itemSemTotal = OrderItem.mount("i2", product, new BigDecimal("50.00"),  null, 1, null);

        Order order = new Order("user-1", "cost-1", OrderStatus.REALIZADO,
                List.of(itemValido, itemSemTotal), buildAddress(), LocalDateTime.now(), LocalDateTime.now().plusDays(5));
        order.calculateTotal();

        assertEquals(new BigDecimal("100.00"), order.getTotal());
    }

    @Test
    void deveGerarCodigoNaoNuloAoCriarPedido() {
        Order order = new Order("user-1", "cost-1", OrderStatus.REALIZADO,
                new ArrayList<>(), buildAddress(), LocalDateTime.now(), LocalDateTime.now().plusDays(5));

        assertNotNull(order.getCode());
        assertFalse(order.getCode().isEmpty());
    }

    @Test
    void codigoGeradoDeveConterSeisDígitos() {
        Order order = new Order("user-1", "cost-1", OrderStatus.REALIZADO,
                new ArrayList<>(), buildAddress(), LocalDateTime.now(), LocalDateTime.now().plusDays(5));

        assertEquals(6, order.getCode().length());
    }

    @Test
    void devePreencherCamposAoCriarPedido() {
        Address address = buildAddress();
        LocalDateTime delivery = LocalDateTime.now().plusDays(2);
        LocalDateTime returnDate = LocalDateTime.now().plusDays(7);

        Order order = new Order("u-1", "c-1", OrderStatus.REALIZADO,
                new ArrayList<>(), address, delivery, returnDate);

        assertEquals("u-1", order.getUserId());
        assertEquals("c-1", order.getCostumerId());
        assertEquals(OrderStatus.REALIZADO, order.getStatus());
        assertEquals(address, order.getDeliveryAddress());
    }

    @Test
    void deveMontarPedidoComTodosOsCampos() {
        LocalDateTime now = LocalDateTime.now();
        Order order = Order.mount("id-1", "u-1", "c-1", now, OrderStatus.CONCLUIDO,
                new BigDecimal("500.00"), new ArrayList<>(), buildAddress(), now, now.plusDays(5), null, "CODE123");

        assertEquals("id-1", order.getId());
        assertEquals(OrderStatus.CONCLUIDO, order.getStatus());
        assertEquals(new BigDecimal("500.00"), order.getTotal());
        assertEquals("CODE123", order.getCode());
    }
}
