package com._s.api.domain.clause.service;

import com._s.api.domain.clause.Clause;
import com._s.api.domain.clause.ClauseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetClauseServiceTest {

    @Mock
    private ClauseRepository repository;

    @InjectMocks
    private GetClauseService service;

    @Test
    void deveRetornarClausulasDoUsuario() {
        List<Clause> clauses = List.of(
                new Clause("Cláusula A", "Texto A", List.of(), "user-1"),
                new Clause("Cláusula B", "Texto B", List.of("§1"), "user-1")
        );
        when(repository.findAllByUserId("user-1")).thenReturn(clauses);

        List<Clause> result = service.getAllByUserId("user-1");

        assertEquals(2, result.size());
        verify(repository).findAllByUserId("user-1");
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaClasulasCadastradas() {
        when(repository.findAllByUserId("user-2")).thenReturn(List.of());

        List<Clause> result = service.getAllByUserId("user-2");

        assertTrue(result.isEmpty());
    }
}
