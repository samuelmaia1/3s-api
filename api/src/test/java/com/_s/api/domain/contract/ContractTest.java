package com._s.api.domain.contract;

import com._s.api.domain.clause.Clause;
import com._s.api.domain.contract.exception.ContractIllegalSignException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContractTest {

    private Contract buildContract() {
        return new Contract("user-1", "costumer-1", "ref-1", ContractReferenceType.ORDER, new ArrayList<>());
    }

    @Test
    void deveCriarContratoComStatusPendente() {
        Contract contract = buildContract();
        assertEquals(ContractStatus.ASSINATURA_PENDENTE, contract.getStatus());
    }

    @Test
    void deveCriarContratoComCodigoNaoNulo() {
        Contract contract = buildContract();
        assertNotNull(contract.getCode());
        assertFalse(contract.getCode().isEmpty());
    }

    @Test
    void codigoDeveConterSeisDígitos() {
        Contract contract = buildContract();
        assertEquals(6, contract.getCode().length());
    }

    @Test
    void deveCriarContratoComCamposCorretos() {
        Contract contract = buildContract();
        assertEquals("user-1", contract.getUserId());
        assertEquals("costumer-1", contract.getCostumerId());
        assertEquals("ref-1", contract.getReferenceId());
        assertEquals(ContractReferenceType.ORDER, contract.getReferenceType());
        assertNotNull(contract.getCreatedAt());
    }

    @Test
    void deveCriarContratoAdicionandoClausulas() {
        Clause clause = new Clause("Título", "Texto principal", List.of("§1"), "user-1");
        Contract contract = new Contract("user-1", "c-1", "ref-1", ContractReferenceType.RENT, List.of(clause));

        assertEquals(1, contract.getClauses().size());
    }

    @Test
    void deveAssinarContratoComStatusPendente() {
        Contract contract = buildContract();
        contract.markAsSigned();
        assertEquals(ContractStatus.ASSINADO, contract.getStatus());
    }

    @Test
    void deveCancelarContratoPendente() {
        Contract contract = buildContract();
        contract.markAsCanceled();
        assertEquals(ContractStatus.CANCELADO, contract.getStatus());
    }

    @Test
    void deveCancelarContratoAssinado() {
        Contract contract = buildContract();
        contract.markAsSigned();
        contract.markAsCanceled();
        assertEquals(ContractStatus.CANCELADO, contract.getStatus());
    }

    @Test
    void deveLancarExcecaoAoAssinarContratoCancelado() {
        Contract contract = buildContract();
        contract.markAsCanceled();
        assertThrows(ContractIllegalSignException.class, contract::markAsSigned);
    }

    @Test
    void deveMontarContratoComTodosOsCampos() {
        LocalDateTime now = LocalDateTime.now();
        Contract contract = Contract.mount(
                "id-1", "CODE123", "user-1", "costumer-1",
                "ref-1", ContractReferenceType.ORDER, ContractStatus.ASSINADO,
                now, new ArrayList<>()
        );

        assertEquals("id-1", contract.getId());
        assertEquals("CODE123", contract.getCode());
        assertEquals(ContractStatus.ASSINADO, contract.getStatus());
        assertEquals(now, contract.getCreatedAt());
    }
}
