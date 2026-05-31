package com._s.api.domain.product.service;

import com._s.api.domain.exception.EntityNotFoundException;
import com._s.api.domain.product.Product;
import com._s.api.domain.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetProductsServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private GetProductsService service;

    @Test
    void executeByUserId_semNome_deveRetornarTodosOsProdutos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = new PageImpl<>(List.of(new Product()));
        when(repository.findAllByUserId("user-1", pageable)).thenReturn(page);

        Page<Product> result = service.executeByUserId("user-1", null, pageable);

        assertEquals(1, result.getTotalElements());
        verify(repository).findAllByUserId("user-1", pageable);
        verify(repository, never()).findByUserIdAndNameContainingIgnoreCase(any(), any(), any());
    }

    @Test
    void executeByUserId_comNome_deveFiltrarPorNome() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = new PageImpl<>(List.of(new Product()));
        when(repository.findByUserIdAndNameContainingIgnoreCase("user-1", "Letreiro", pageable)).thenReturn(page);

        Page<Product> result = service.executeByUserId("user-1", "Letreiro", pageable);

        assertEquals(1, result.getTotalElements());
        verify(repository).findByUserIdAndNameContainingIgnoreCase("user-1", "Letreiro", pageable);
        verify(repository, never()).findAllByUserId(any(), any());
    }

    @Test
    void executeByUserId_comNomeEmBranco_deveRetornarTodos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = new PageImpl<>(List.of());
        when(repository.findAllByUserId("user-1", pageable)).thenReturn(page);

        service.executeByUserId("user-1", "   ", pageable);

        verify(repository).findAllByUserId("user-1", pageable);
    }

    @Test
    void executeById_deveRetornarProdutoExistente() {
        Product product = new Product();
        when(repository.findById("prod-1")).thenReturn(Optional.of(product));

        Product result = service.executeById("prod-1");

        assertEquals(product, result);
    }

    @Test
    void executeById_deveLancarExcecaoQuandoProdutoNaoEncontrado() {
        when(repository.findById("inexistente")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.executeById("inexistente"));
    }
}
