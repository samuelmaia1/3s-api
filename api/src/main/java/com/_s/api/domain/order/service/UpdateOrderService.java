package com._s.api.domain.order.service;

import com._s.api.domain.contract.Contract;
import com._s.api.domain.contract.ContractRepository;
import com._s.api.domain.contract.service.UpdateContractService;
import com._s.api.domain.order.Order;
import com._s.api.domain.order.OrderRepository;
import com._s.api.domain.order.OrderStatus;
import com._s.api.domain.order.exception.OrderNotFoundException;

import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class UpdateOrderService {

    private final OrderRepository repository;
    private final UpdateContractService updateContractService;
    private final ContractRepository contractRepository;

    public UpdateOrderService(
        OrderRepository repository, 
        UpdateContractService updateContractService,
        ContractRepository contractRepository
    ) {
        this.repository = repository;
        this.updateContractService = updateContractService;
        this.contractRepository = contractRepository;
    }

    @Transactional
    public Order updateStatus(String id, OrderStatus status) {
        if (status == OrderStatus.CANCELADO) {
            Optional<Contract> contract = contractRepository.findByOrderId(id);

            if (contract.isPresent())
                updateContractService.cancelContract(contract.get().getId());
        }

        repository.updateStatus(id, status);

        return repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado."));
    }
}
