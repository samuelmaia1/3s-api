package com._s.api.domain.contract.service;

import com._s.api.domain.contract.Contract;
import com._s.api.domain.contract.ContractRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetContractService {

    private final ContractRepository repository;

    public GetContractService(ContractRepository repository) {
        this.repository = repository;
    }

    public List<Contract> executeByUserId(String userId) {
        return repository.findAllByUserId(userId);
    }
}
