package com._s.api.domain.rent;

import com._s.api.domain.product.Product;
import com._s.api.domain.rentItem.RentItem;
import com._s.api.domain.shared.Address;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RentTest {

    private Product buildProduct() {
        Product p = new Product();
        p.setName("Produto Aluguel");
        p.setStock(30);
        p.setPrice(new BigDecimal("80.00"));
        return p;
    }

    private Address buildAddress() {
        return Address.mount("30130-010", "Rua B", "Bairro", "Cidade", "10");
    }

    @Test
    void deveCalcularTotalComItensEDeliveryTax() {
        Product product = buildProduct();
        RentItem item = RentItem.mount("ri1", product, new BigDecimal("80.00"), new BigDecimal("240.00"), 3, null);

        Rent rent = new Rent("u-1", "c-1", RentStatus.REALIZADO,
                List.of(item), new BigDecimal("50.00"), buildAddress(),
                LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        rent.calculateTotal();

        assertEquals(new BigDecimal("290.00"), rent.getTotal());
    }

    @Test
    void deveCalcularTotalSemDeliveryTax() {
        Product product = buildProduct();
        RentItem item = RentItem.mount("ri1", product, new BigDecimal("80.00"), new BigDecimal("160.00"), 2, null);

        Rent rent = new Rent("u-1", "c-1", RentStatus.REALIZADO,
                List.of(item), null, buildAddress(),
                LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        rent.calculateTotal();

        assertEquals(new BigDecimal("160.00"), rent.getTotal());
    }

    @Test
    void deveCalcularTotalComListaVaziaEDeliveryTax() {
        Rent rent = new Rent("u-1", "c-1", RentStatus.REALIZADO,
                new ArrayList<>(), new BigDecimal("30.00"), buildAddress(),
                LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        rent.calculateTotal();

        assertEquals(new BigDecimal("30.00"), rent.getTotal());
    }

    @Test
    void deveCalcularTotalZeroComListaVaziaESemTaxa() {
        Rent rent = new Rent("u-1", "c-1", RentStatus.REALIZADO,
                new ArrayList<>(), null, buildAddress(),
                LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        rent.calculateTotal();

        assertEquals(BigDecimal.ZERO, rent.getTotal());
    }

    @Test
    void deveIgnorarItensComSubTotalNulo() {
        Product product = buildProduct();
        RentItem itemValido   = RentItem.mount("ri1", product, new BigDecimal("80.00"), new BigDecimal("80.00"), 1, null);
        RentItem itemSemTotal = RentItem.mount("ri2", product, new BigDecimal("80.00"), null, 1, null);

        Rent rent = new Rent("u-1", "c-1", RentStatus.REALIZADO,
                List.of(itemValido, itemSemTotal), BigDecimal.ZERO, buildAddress(),
                LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        rent.calculateTotal();

        assertEquals(new BigDecimal("80.00"), rent.getTotal());
    }

    @Test
    void deveGerarCodigoNaoNuloAoCriarAluguel() {
        Rent rent = new Rent("u-1", "c-1", RentStatus.REALIZADO,
                new ArrayList<>(), BigDecimal.ZERO, buildAddress(),
                LocalDateTime.now(), LocalDateTime.now().plusDays(7));

        assertNotNull(rent.getCode());
        assertEquals(6, rent.getCode().length());
    }

    @Test
    void deveMontarAluguelComTodosOsCampos() {
        LocalDateTime now = LocalDateTime.now();
        Rent rent = Rent.mount("id-r", "u-1", "c-1", now, RentStatus.CONCLUIDO,
                new BigDecimal("300.00"), new BigDecimal("20.00"),
                new ArrayList<>(), buildAddress(), now, now.plusDays(10), null, "CODE456");

        assertEquals("id-r", rent.getId());
        assertEquals(RentStatus.CONCLUIDO, rent.getStatus());
        assertEquals(new BigDecimal("300.00"), rent.getTotal());
        assertEquals(new BigDecimal("20.00"), rent.getDeliveryTax());
        assertEquals("CODE456", rent.getCode());
    }
}
