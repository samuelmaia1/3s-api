package com._s.api.domain.contract.service;

import com._s.api.domain.contract.Contract;
import com._s.api.domain.contract.ContractRepository;
import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.costumer.service.GetCostumerService;
import com._s.api.presentation.response.ContractResponseSummary;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetContractService {

    private final ContractRepository repository;
    private final GetCostumerService getCostumerService;

    public Page<ContractResponseSummary> executeByUserId(String userId, Pageable pageable) {
        Page<Contract> contractsPage = repository.findAllByUserId(userId, pageable);
        List<Contract> contracts = contractsPage.getContent();

        if (contracts.isEmpty()) {
            return Page.empty(pageable);
        }

        List<String> costumerIds = contracts.stream().map(Contract::getCostumerId).distinct().toList();

        Map<String, Costumer> costumersMap = getCostumerService.executeByIds(costumerIds)
                .stream()
                .collect(Collectors.toMap(Costumer::getId, Function.identity()));

        List<ContractResponseSummary> response = contracts.stream()
                .map(contract -> new ContractResponseSummary(contract, costumersMap.get(contract.getCostumerId())))
                .toList();

        return new PageImpl<>(response, pageable, contractsPage.getTotalElements());
    }
}
