package com._s.api.domain.rent.service;

import com._s.api.domain.contract.Contract;
import com._s.api.domain.contract.ContractReferenceType;
import com._s.api.domain.contract.ContractRepository;
import com._s.api.domain.contract.service.UpdateContractService;
import com._s.api.domain.rent.Rent;
import com._s.api.domain.rent.RentRepository;
import com._s.api.domain.rent.RentStatus;
import com._s.api.domain.rent.exception.RentNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateRentService {

    private final RentRepository repository;
    private final ContractRepository contractRepository;
    private final UpdateContractService updateContractService;

    public UpdateRentService(
            RentRepository repository,
            ContractRepository contractRepository,
            UpdateContractService updateContractService
    ) {
        this.repository = repository;
        this.contractRepository = contractRepository;
        this.updateContractService = updateContractService;
    }

    @Transactional
    public Rent updateStatus(String id, RentStatus status, String userId) {
        if (status == RentStatus.CANCELADO) {
            cancelRent(id, userId);
        }

        repository.updateStatus(id, status);

        return repository.findById(id)
                .orElseThrow(() -> new RentNotFoundException("Aluguel não encontrado."));
    }

    public void cancelRent(String id, String userId) {
        Optional<Rent> optionalRent = repository.findById(id);

        if (optionalRent.isEmpty()) {
            throw new RentNotFoundException("Aluguel não encontrado.");
        }

        Optional<Contract> contract =
                contractRepository.findByReferenceIdAndReferenceType(id, ContractReferenceType.RENT);

        if (contract.isPresent()) {
            updateContractService.cancelContract(contract.get().getId());
        }
    }
}
