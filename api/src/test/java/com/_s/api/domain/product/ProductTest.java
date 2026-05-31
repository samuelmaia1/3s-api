package com._s.api.domain.product;

import com._s.api.domain.product.service.CreateProductCommand;
import com._s.api.domain.product.service.UpdateProductCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setName("Letreiro LED");
        product.setDescription("Letreiro luminoso");
        product.setPrice(new BigDecimal("150.00"));
        product.setStock(10);
        product.setImageUri("https://exemplo.com/img.png");
    }

    @Test
    void deveDecrementarEstoqueCorretamente() {
        product.decreaseStock(3);
        assertEquals(7, product.getStock());
    }

    @Test
    void deveDecrementarEstoqueParaZero() {
        product.decreaseStock(10);
        assertEquals(0, product.getStock());
    }

    @Test
    void deveIncrementarEstoqueCorretamente() {
        product.incrementStock(5);
        assertEquals(15, product.getStock());
    }

    @Test
    void deveCriarProdutoAPartirDeCommand() {
        CreateProductCommand command = new CreateProductCommand(
                "Placa Neon", "Placa decorativa", new BigDecimal("200.00"), 20, "https://img.com/neon.png"
        );
        Product p = new Product(command);

        assertEquals("Placa Neon", p.getName());
        assertEquals("Placa decorativa", p.getDescription());
        assertEquals(new BigDecimal("200.00"), p.getPrice());
        assertEquals(20, p.getStock());
        assertEquals("https://img.com/neon.png", p.getImageUri());
    }

    @Test
    void deveAtualizarTodosCamposQuandoUpdateCommandTemTodosPreenchidos() {
        UpdateProductCommand command = new UpdateProductCommand(
                null, "Novo Nome", "Nova Descricao", 50, "https://nova.com/img.png", new BigDecimal("300.00")
        );
        product.update(command);

        assertEquals("Novo Nome", product.getName());
        assertEquals("Nova Descricao", product.getDescription());
        assertEquals(50, product.getStock());
        assertEquals("https://nova.com/img.png", product.getImageUri());
        assertEquals(new BigDecimal("300.00"), product.getPrice());
    }

    @Test
    void deveManterCamposOriginaisQuandoUpdateCommandTemNulos() {
        UpdateProductCommand command = new UpdateProductCommand(null, null, null, null, null, null);
        product.update(command);

        assertEquals("Letreiro LED", product.getName());
        assertEquals("Letreiro luminoso", product.getDescription());
        assertEquals(10, product.getStock());
        assertEquals(new BigDecimal("150.00"), product.getPrice());
    }

    @Test
    void deveAtualizarApenasNomeQuandoSomenteNomeEstaPreenchido() {
        UpdateProductCommand command = new UpdateProductCommand(null, "Letreiro Novo", null, null, null, null);
        product.update(command);

        assertEquals("Letreiro Novo", product.getName());
        assertEquals("Letreiro luminoso", product.getDescription());
        assertEquals(10, product.getStock());
    }
}
