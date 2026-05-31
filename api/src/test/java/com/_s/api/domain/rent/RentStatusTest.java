package com._s.api.domain.rent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RentStatusTest {

    @Test
    void deveRetornarStatusPeloNomeExato() {
        assertEquals(RentStatus.REALIZADO, RentStatus.fromValue("REALIZADO"));
    }

    @Test
    void deveRetornarStatusPeloNomeLowercase() {
        assertEquals(RentStatus.CANCELADO, RentStatus.fromValue("cancelado"));
    }

    @Test
    void deveRetornarStatusPeloLabel() {
        assertEquals(RentStatus.REALIZADO, RentStatus.fromValue("Realizado"));
    }

    @Test
    void deveRetornarStatusContratoAssinado() {
        assertEquals(RentStatus.CONTRATO_ASSINADO, RentStatus.fromValue("Contrato Assinado"));
    }

    @Test
    void deveRetornarStatusDevolucaoAtrasada() {
        assertEquals(RentStatus.DEVOLUCAO_ATRASADA, RentStatus.fromValue("Devolução Atrasada"));
    }

    @Test
    void deveRetornarStatusConcluido() {
        assertEquals(RentStatus.CONCLUIDO, RentStatus.fromValue("Concluído"));
    }

    @Test
    void deveLancarExcecaoParaStatusInvalido() {
        assertThrows(IllegalArgumentException.class, () -> RentStatus.fromValue("INVALIDO"));
    }

    @Test
    void deveRetornarLabelCorreto() {
        assertEquals("Realizado", RentStatus.REALIZADO.getLabel());
        assertEquals("Cancelado", RentStatus.CANCELADO.getLabel());
        assertEquals("Devolução Atrasada", RentStatus.DEVOLUCAO_ATRASADA.getLabel());
    }

    @Test
    void deveTerOitoEstados() {
        assertEquals(8, RentStatus.values().length);
    }
}
