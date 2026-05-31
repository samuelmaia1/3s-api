package com._s.api.domain.order.service;

import com._s.api.domain.order.Order;
import com._s.api.domain.order.OrderFilter;
import com._s.api.domain.order.OrderRepository;
import com._s.api.domain.order.exception.OrderNotFoundException;
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
class GetOrderServiceTest {

    @Mock
    private OrderRepository repository;

    @InjectMocks
    private GetOrderService service;

    @Test
    void executeByUserId_deveRetornarPaginaDePedidos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> page = new PageImpl<>(List.of(mock(Order.class)));
        when(repository.findAllByUserId("user-1", pageable)).thenReturn(page);

        Page<Order> result = service.executeByUserId("user-1", pageable);

        assertEquals(1, result.getTotalElements());
        verify(repository).findAllByUserId("user-1", pageable);
    }

    @Test
    void executeByUserId_comFiltro_devePassarFiltro() {
        Pageable pageable = PageRequest.of(0, 10);
        OrderFilter filter = mock(OrderFilter.class);
        Page<Order> page = new PageImpl<>(List.of());
        when(repository.findAllByUserId("user-1", filter, pageable)).thenReturn(page);

        service.executeByUserId("user-1", filter, pageable);

        verify(repository).findAllByUserId("user-1", filter, pageable);
    }

    @Test
    void executeByCostumerId_deveRetornarPedidosDoCliente() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> page = new PageImpl<>(List.of(mock(Order.class), mock(Order.class)));
        when(repository.findAllByCostumerId("c-1", pageable)).thenReturn(page);

        Page<Order> result = service.executeByCostumerId("c-1", pageable);

        assertEquals(2, result.getTotalElements());
    }

    @Test
    void execute_deveRetornarPedidoExistente() {
        Order order = mock(Order.class);
        when(repository.findById("order-1")).thenReturn(Optional.of(order));

        Order result = service.execute("order-1");

        assertEquals(order, result);
    }

    @Test
    void execute_deveLancarExcecaoQuandoPedidoNaoExiste() {
        when(repository.findById("inexistente")).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> service.execute("inexistente"));
    }
}
