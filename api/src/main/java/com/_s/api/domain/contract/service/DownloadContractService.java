package com._s.api.domain.contract.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com._s.api.domain.contract.Contract;
import com._s.api.domain.contract.ContractReferenceType;
import com._s.api.domain.contract.ContractRepository;
import com._s.api.domain.contract.exception.ContractNotFoundException;
import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.costumer.service.GetCostumerService;
import com._s.api.domain.order.Order;
import com._s.api.domain.order.service.GetOrderService;
import com._s.api.domain.rent.Rent;
import com._s.api.domain.rent.service.GetRentService;
import com._s.api.domain.user.User;
import com._s.api.domain.user.service.GetUserService;
import com._s.api.presentation.dto.CreatedContract;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DownloadContractService {

    private final GetOrderService getOrderService;
    private final GetRentService getRentService;
    private final GetCostumerService getCostumerService;
    private final ContractRepository contractRepository;
    private final GetUserService getUserService;
    private final GenerateService generateService;

    public CreatedContract execute(String code, String userId) {

        Optional<Contract> optionalContract = contractRepository.findByCode(code);

        if (optionalContract.isEmpty()) {
            throw new ContractNotFoundException("Contrato não encontrado.");
        }

        Contract contract = optionalContract.get();

        Costumer costumer = getCostumerService.execute(contract.getCostumerId());
        User user = getUserService.executeById(userId);
        Object reference = resolveReference(contract.getReferenceId(), contract.getReferenceType());

        byte[] pdf = generateService.generatePdf(contract, reference, costumer, user);


        return new CreatedContract(pdf, contract);
    }

    private Object resolveReference(String referenceId, ContractReferenceType referenceType) {
        if (referenceType == ContractReferenceType.ORDER) {
            Order order = getOrderService.execute(referenceId);
            return order;
        }

        Rent rent = getRentService.execute(referenceId);
        return rent;
    }
}
