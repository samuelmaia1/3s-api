package com._s.api.domain.contract.service;

import com._s.api.domain.contract.Contract;
import com._s.api.domain.contract.ContractRepository;
import com._s.api.domain.contract.ContractReferenceType;
import com._s.api.domain.contract.exception.ContractNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateContractServiceTest {

    @Mock
    private ContractRepository repository;

    @InjectMocks
    private UpdateContractService service;

    @Test
    void signContract_deveAssinarContratoExistente() {
        Contract contract = new Contract("user-1", "c-1", "ref-1", ContractReferenceType.ORDER, new ArrayList<>());
        when(repository.findById("contract-1")).thenReturn(Optional.of(contract));

        service.signContract("contract-1");

        verify(repository).save(contract);
    }

    @Test
    void signContract_deveLancarExcecaoQuandoContratoNaoExiste() {
        when(repository.findById("inexistente")).thenReturn(Optional.empty());

        assertThrows(ContractNotFoundException.class, () -> service.signContract("inexistente"));
        verify(repository, never()).save(any());
    }

    @Test
    void cancelContract_deveCancelarContratoExistente() {
        Contract contract = new Contract("user-1", "c-1", "ref-1", ContractReferenceType.RENT, new ArrayList<>());
        when(repository.findById("contract-2")).thenReturn(Optional.of(contract));

        service.cancelContract("contract-2");

        verify(repository).save(contract);
    }

    @Test
    void cancelContract_deveLancarExcecaoQuandoContratoNaoExiste() {
        when(repository.findById("inexistente")).thenReturn(Optional.empty());

        assertThrows(ContractNotFoundException.class, () -> service.cancelContract("inexistente"));
        verify(repository, never()).save(any());
    }
}
