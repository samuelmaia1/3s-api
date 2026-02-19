package com._s.api.domain.contract.service;

import com._s.api.domain.contract.Contract;
import com._s.api.domain.contract.ContractRepository;
import com._s.api.domain.exception.EntityNotFoundException;
import com._s.api.domain.order.OrderStatus;
import com._s.api.domain.order.service.UpdateOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UpdateContractService {

    private final ContractRepository repository;
    private final UpdateOrderService updateOrderService;

    public void signContract(String id){
        Optional<Contract> optionalContract = repository.findById(id);

        if (optionalContract.isEmpty())
            throw new EntityNotFoundException("Contrato não encontrado.");

        Contract contract = optionalContract.get();

        contract.markAsSigned();

        updateOrderService.updateStatus(contract.getOrderId(), OrderStatus.CONTRATO_ASSINADO);

        repository.save(contract);
    }
}
