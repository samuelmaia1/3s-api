package com._s.api.domain.rent.service;

import com._s.api.domain.rent.Rent;
import com._s.api.domain.rent.RentRepository;
import com._s.api.domain.rent.exception.RentNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetRentService {

    private final RentRepository repository;

    public GetRentService(RentRepository repository) {
        this.repository = repository;
    }

    public Page<Rent> executeByUserId(String id, Pageable pageable) {
        return repository.findAllByUserId(id, pageable);
    }

    public Page<Rent> executeByCostumerId(String id, Pageable pageable) {
        return repository.findAllByCostumerId(id, pageable);
    }

    public Rent execute(String id) {
        Optional<Rent> optionalRent = repository.findById(id);

        if (optionalRent.isEmpty()) {
            throw new RentNotFoundException("Aluguel não encontrado.");
        }

        return optionalRent.get();
    }
}
