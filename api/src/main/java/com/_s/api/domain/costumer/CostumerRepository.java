package com._s.api.domain.costumer;

import com._s.api.domain.valueobject.Cpf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CostumerRepository {
    Costumer save(Costumer costumer);
    Optional<Costumer> findById(String id);
    Optional<Costumer> findByCpf(String cpf);
    Boolean existsByCpf(Cpf cpf);
    List<Costumer> findByIdIn(List<String> ids);
    Page<Costumer> findAllByUserId(String userId, Pageable pageable);
    Page<Costumer> findByUserIdAndNameContainingIgnoreCase(String userId, String name, Pageable pageable);
}
