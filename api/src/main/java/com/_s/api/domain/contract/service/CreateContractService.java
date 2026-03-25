package com._s.api.domain.contract.service;

import com._s.api.domain.clause.Clause;
import com._s.api.domain.clause.ClauseRepository;
import com._s.api.domain.contract.Contract;
import com._s.api.domain.contract.ContractRepository;
import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.costumer.service.GetCostumerService;
import com._s.api.domain.order.Order;
import com._s.api.domain.order.service.GetOrderService;
import com._s.api.domain.user.User;
import com._s.api.domain.user.service.GetUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateContractService {

    private final GetOrderService getOrderService;
    private final GetCostumerService getCostumerService;
    private final ContractRepository contractRepository;
    private final ClauseRepository clauseRepository;
    private final GetUserService getUserService;
    private final GenerateService generateService;

    public byte[] execute(CreateContractCommand data, String userId) {
        Optional<Contract> optionalContract = contractRepository.findByOrderId(data.getOrderId());
        if (optionalContract.isPresent()) {
            contractRepository.delete(optionalContract.get());
        }

        Order order = getOrderService.execute(data.getOrderId());
        Costumer costumer = getCostumerService.execute(data.getCostumerId());
        User user = getUserService.executeById(userId);

        List<Clause> clauses = data
            .getClausesIds()
            .stream()
            .map(id -> clauseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cláusula não encontrada")))
            .toList();
        ;

        Contract contract = new Contract(
            costumer.getId(),
            order.getId(),
            clauses
        );

        Contract saveContract = contractRepository.save(contract);

        return generateService.generatePdf(saveContract, order, costumer, user);
    }

    

}
