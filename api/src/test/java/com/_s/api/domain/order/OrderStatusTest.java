package com._s.api.domain.order;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {

    @Test
    void deveRetornarStatusPeloNomeExato() {
        assertEquals(OrderStatus.REALIZADO, OrderStatus.fromValue("REALIZADO"));
    }

    @Test
    void deveRetornarStatusPeloNomeLowercase() {
        assertEquals(OrderStatus.CANCELADO, OrderStatus.fromValue("cancelado"));
    }

    @Test
    void deveRetornarStatusPeloLabel() {
        assertEquals(OrderStatus.REALIZADO, OrderStatus.fromValue("Realizado"));
    }

    @Test
    void deveRetornarStatusContratoAssinado() {
        assertEquals(OrderStatus.CONTRATO_ASSINADO, OrderStatus.fromValue("Contrato Assinado"));
    }

    @Test
    void deveRetornarStatusPagamentoAprovado() {
        assertEquals(OrderStatus.PAGAMENTO_APROVADO, OrderStatus.fromValue("Pagamento Aprovado"));
    }

    @Test
    void deveRetornarStatusAguardandoEntrega() {
        assertEquals(OrderStatus.AGUARDANDO_ENTREGA, OrderStatus.fromValue("Aguardando Entrega"));
    }

    @Test
    void deveRetornarStatusEntregue() {
        assertEquals(OrderStatus.ENTREGUE, OrderStatus.fromValue("Entregue"));
    }

    @Test
    void deveRetornarStatusConcluido() {
        assertEquals(OrderStatus.CONCLUIDO, OrderStatus.fromValue("Concluído"));
    }

    @Test
    void deveLancarExcecaoParaStatusInvalido() {
        assertThrows(IllegalArgumentException.class, () -> OrderStatus.fromValue("INEXISTENTE"));
    }

    @Test
    void deveRetornarLabelCorreto() {
        assertEquals("Realizado", OrderStatus.REALIZADO.getLabel());
        assertEquals("Cancelado", OrderStatus.CANCELADO.getLabel());
        assertEquals("Concluído", OrderStatus.CONCLUIDO.getLabel());
    }
}
