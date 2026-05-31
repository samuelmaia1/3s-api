package com._s.api.domain.rent.service;

import com._s.api.domain.rent.Rent;
import com._s.api.domain.rent.RentFilter;
import com._s.api.domain.rent.RentRepository;
import com._s.api.domain.rent.exception.RentNotFoundException;
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
class GetRentServiceTest {

    @Mock
    private RentRepository repository;

    @InjectMocks
    private GetRentService service;

    @Test
    void executeByUserId_deveRetornarPaginaDeAlugueis() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Rent> page = new PageImpl<>(List.of(mock(Rent.class)));
        when(repository.findAllByUserId("user-1", pageable)).thenReturn(page);

        Page<Rent> result = service.executeByUserId("user-1", pageable);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void executeByUserId_comFiltro_devePassarFiltro() {
        Pageable pageable = PageRequest.of(0, 10);
        RentFilter filter = mock(RentFilter.class);
        Page<Rent> page = new PageImpl<>(List.of());
        when(repository.findAllByUserId("user-1", filter, pageable)).thenReturn(page);

        service.executeByUserId("user-1", filter, pageable);

        verify(repository).findAllByUserId("user-1", filter, pageable);
    }

    @Test
    void executeByCostumerId_deveRetornarAlugueisDoCliente() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Rent> page = new PageImpl<>(List.of(mock(Rent.class), mock(Rent.class)));
        when(repository.findAllByCostumerId("c-1", pageable)).thenReturn(page);

        Page<Rent> result = service.executeByCostumerId("c-1", pageable);

        assertEquals(2, result.getTotalElements());
    }

    @Test
    void execute_deveRetornarAluguelExistente() {
        Rent rent = mock(Rent.class);
        when(repository.findById("rent-1")).thenReturn(Optional.of(rent));

        Rent result = service.execute("rent-1");

        assertEquals(rent, result);
    }

    @Test
    void execute_deveLancarExcecaoQuandoAluguelNaoExiste() {
        when(repository.findById("inexistente")).thenReturn(Optional.empty());

        assertThrows(RentNotFoundException.class, () -> service.execute("inexistente"));
    }
}
