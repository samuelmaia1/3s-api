package com._s.api.domain.rent.service;

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

    public UpdateRentService(RentRepository repository) {
        this.repository = repository;
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
    }
}
