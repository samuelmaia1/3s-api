package com._s.api.domain.order.service;

import com._s.api.domain.contract.ContractReferenceType;
import com._s.api.domain.contract.ContractRepository;
import com._s.api.domain.contract.service.UpdateContractService;
import com._s.api.domain.order.Order;
import com._s.api.domain.order.OrderRepository;
import com._s.api.domain.order.OrderStatus;
import com._s.api.domain.order.exception.OrderNotFoundException;
import com._s.api.domain.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UpdateOrderServiceTest {

    @Mock
    private OrderRepository repository;

    @Mock
    private UpdateContractService updateContractService;

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UpdateOrderService service;

    @Test
    void updateStatus_deveAtualizarStatusDosPedido() {
        Order order = mock(Order.class);
        when(order.getItems()).thenReturn(new ArrayList<>());
        when(repository.findById("order-1")).thenReturn(Optional.of(order));

        service.updateStatus("order-1", OrderStatus.PAGAMENTO_APROVADO, "user-1");

        verify(repository).updateStatus("order-1", OrderStatus.PAGAMENTO_APROVADO);
        verify(repository, times(1)).findById("order-1");
    }

    @Test
    void updateStatus_comCancelado_deveCancelarPedidoEChamarCancelOrder() {
        Order order = mock(Order.class);
        when(order.getItems()).thenReturn(new ArrayList<>());
        when(repository.findById("order-1")).thenReturn(Optional.of(order));
        when(contractRepository.findByReferenceIdAndReferenceType("order-1", ContractReferenceType.ORDER))
                .thenReturn(Optional.empty());

        service.updateStatus("order-1", OrderStatus.CANCELADO, "user-1");

        verify(repository).updateStatus("order-1", OrderStatus.CANCELADO);
    }

    @Test
    void cancelOrder_deveLancarExcecaoQuandoPedidoNaoExiste() {
        when(repository.findById("inexistente")).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,
                () -> service.cancelOrder("inexistente", "user-1"));
    }

    @Test
    void cancelOrder_semContrato_deveApenasIncrementarEstoque() {
        Order order = mock(Order.class);
        when(order.getItems()).thenReturn(new ArrayList<>());
        when(repository.findById("order-1")).thenReturn(Optional.of(order));
        when(contractRepository.findByReferenceIdAndReferenceType("order-1", ContractReferenceType.ORDER))
                .thenReturn(Optional.empty());

        service.cancelOrder("order-1", "user-1");

        verify(updateContractService, never()).cancelContract(any());
        verify(productRepository).saveAll(anyList(), eq("user-1"));
    }
}
