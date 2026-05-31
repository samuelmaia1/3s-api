package com._s.api.domain.product.service;

import com._s.api.domain.product.Product;
import com._s.api.domain.product.ProductPolicy;
import com._s.api.domain.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductPolicy productPolicy;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private CreateProductService service;

    @Test
    void deveCriarProdutoComSucesso() {
        CreateProductCommand command = new CreateProductCommand(
                "Letreiro LED", "Letreiro luminoso", new BigDecimal("150.00"), 10, null
        );
        Product savedProduct = new Product(command);
        when(repository.save(any(Product.class), anyString())).thenReturn(savedProduct);

        Product result = service.execute(command, "user-1");

        assertNotNull(result);
        verify(productPolicy).validateFields(command);
        verify(repository).save(any(Product.class), eq("user-1"));
        verify(messagingTemplate).convertAndSend(eq("/topic/products"), any(Product.class));
    }

    @Test
    void deveValidarCamposAntesDeSalvar() {
        CreateProductCommand command = new CreateProductCommand(
                "Placa", "Descricao", new BigDecimal("50.00"), 5, null
        );
        when(repository.save(any(), anyString())).thenReturn(new Product(command));

        service.execute(command, "user-1");

        var inOrder = inOrder(productPolicy, repository);
        inOrder.verify(productPolicy).validateFields(command);
        inOrder.verify(repository).save(any(), anyString());
    }

    @Test
    void devePublicarNaFilaWebSocket() {
        CreateProductCommand command = new CreateProductCommand("P", "D", BigDecimal.ONE, 1, null);
        Product product = new Product(command);
        when(repository.save(any(), anyString())).thenReturn(product);

        service.execute(command, "user-1");

        verify(messagingTemplate).convertAndSend("/topic/products", product);
    }
}
