package com._s.api.domain.product.service;

import com._s.api.domain.product.Product;
import com._s.api.domain.product.ProductRepository;
import com._s.api.domain.product.exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private UpdateProductService service;

    @Test
    void deveAtualizarProdutoComSucesso() {
        Product product = new Product();
        product.setName("Antigo");
        product.setStock(5);
        product.setPrice(new BigDecimal("100.00"));

        UpdateProductCommand command = new UpdateProductCommand("prod-1", "Novo Nome", null, null, null, null);

        when(repository.findById("prod-1")).thenReturn(Optional.of(product));
        when(repository.save(any(Product.class), anyString())).thenReturn(product);

        Product result = service.execute(command, "user-1");

        assertNotNull(result);
        verify(repository).save(product, "user-1");
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoExiste() {
        UpdateProductCommand command = new UpdateProductCommand("inexistente", "Nome", null, null, null, null);
        when(repository.findById("inexistente")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> service.execute(command, "user-1"));
        verify(repository, never()).save(any(), any());
    }
}
