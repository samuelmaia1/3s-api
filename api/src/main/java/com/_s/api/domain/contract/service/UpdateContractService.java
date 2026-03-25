package com._s.api.domain.contract.service;

import com._s.api.domain.contract.Contract;
import com._s.api.domain.contract.ContractRepository;
import com._s.api.domain.contract.exception.ContractNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UpdateContractService {

    private final ContractRepository repository;

    public void signContract(String id){
        Optional<Contract> optionalContract = repository.findById(id);

        if (optionalContract.isEmpty())
            throw new ContractNotFoundException("Contrato não encontrado.");

        Contract contract = optionalContract.get();

        contract.markAsSigned();

        repository.save(contract);
    }

    public void cancelContract(String id){
        Optional<Contract> optionalContract = repository.findById(id);

        if (optionalContract.isEmpty())
            throw new ContractNotFoundException("Contrato não encontrado.");

        Contract contract = optionalContract.get();

        contract.markAsCanceled();

        repository.save(contract);
    }
}
