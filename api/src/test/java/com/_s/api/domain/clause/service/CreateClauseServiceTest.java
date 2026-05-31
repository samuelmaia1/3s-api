package com._s.api.domain.clause.service;

import com._s.api.domain.clause.Clause;
import com._s.api.domain.clause.ClauseRepository;
import com._s.api.presentation.dto.ClauseRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateClauseServiceTest {

    @Mock
    private ClauseRepository repository;

    @InjectMocks
    private CreateClauseService service;

    @Test
    void deveCriarClausulasAPartirDaRequisicao() {
        ClauseRequest req1 = new ClauseRequest();
        req1.setTitle("Cláusula 1");
        req1.setMainText("Texto principal");
        req1.setParagraphs(List.of("§1", "§2"));

        ClauseRequest req2 = new ClauseRequest();
        req2.setTitle("Cláusula 2");
        req2.setMainText("Outro texto");
        req2.setParagraphs(List.of());

        List<Clause> savedClauses = List.of(
                new Clause("Cláusula 1", "Texto principal", List.of("§1", "§2"), "user-1"),
                new Clause("Cláusula 2", "Outro texto", List.of(), "user-1")
        );
        when(repository.saveAll(anyList())).thenReturn(savedClauses);

        List<Clause> result = service.execute(List.of(req1, req2), "user-1");

        assertEquals(2, result.size());
        verify(repository).saveAll(anyList());
    }

    @Test
    void deveCriarListaVaziaSeRequisicaoEstaVazia() {
        when(repository.saveAll(anyList())).thenReturn(List.of());

        List<Clause> result = service.execute(List.of(), "user-1");

        assertTrue(result.isEmpty());
        verify(repository).saveAll(anyList());
    }
}
