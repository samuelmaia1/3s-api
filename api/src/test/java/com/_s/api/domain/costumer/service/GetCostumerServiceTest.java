package com._s.api.domain.costumer.service;

import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.costumer.CostumerNotFoundException;
import com._s.api.domain.costumer.CostumerRepository;
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
class GetCostumerServiceTest {

    @Mock
    private CostumerRepository repository;

    @InjectMocks
    private GetCostumerService service;

    @Test
    void execute_deveRetornarClienteExistente() {
        Costumer costumer = mock(Costumer.class);
        when(repository.findById("c-1")).thenReturn(Optional.of(costumer));

        Costumer result = service.execute("c-1");

        assertEquals(costumer, result);
    }

    @Test
    void execute_deveLancarExcecaoQuandoClienteNaoExiste() {
        when(repository.findById("inexistente")).thenReturn(Optional.empty());

        assertThrows(CostumerNotFoundException.class, () -> service.execute("inexistente"));
    }

    @Test
    void executeByUserId_semNome_deveRetornarTodosClientes() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Costumer> page = new PageImpl<>(List.of(mock(Costumer.class)));
        when(repository.findAllByUserId("user-1", pageable)).thenReturn(page);

        Page<Costumer> result = service.executeByUserId("user-1", null, pageable);

        assertEquals(1, result.getTotalElements());
        verify(repository).findAllByUserId("user-1", pageable);
        verify(repository, never()).findByUserIdAndNameContainingIgnoreCase(any(), any(), any());
    }

    @Test
    void executeByUserId_comNome_deveFiltrarPorNome() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Costumer> page = new PageImpl<>(List.of());
        when(repository.findByUserIdAndNameContainingIgnoreCase("user-1", "João", pageable)).thenReturn(page);

        service.executeByUserId("user-1", "João", pageable);

        verify(repository).findByUserIdAndNameContainingIgnoreCase("user-1", "João", pageable);
    }

    @Test
    void executeByUserId_comNomeEmBranco_deveRetornarTodos() {
        Pageable pageable = PageRequest.of(0, 10);
        when(repository.findAllByUserId("user-1", pageable)).thenReturn(new PageImpl<>(List.of()));

        service.executeByUserId("user-1", "  ", pageable);

        verify(repository).findAllByUserId("user-1", pageable);
    }

    @Test
    void executeByIds_deveRetornarClientesPorListaDeIds() {
        List<String> ids = List.of("c-1", "c-2");
        List<Costumer> costumers = List.of(mock(Costumer.class), mock(Costumer.class));
        when(repository.findByIdIn(ids)).thenReturn(costumers);

        List<Costumer> result = service.executeByIds(ids);

        assertEquals(2, result.size());
        verify(repository).findByIdIn(ids);
    }
}
