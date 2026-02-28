package com._s.api.domain.costumer;

import com._s.api.domain.valueobject.Cpf;

import java.util.List;
import java.util.Optional;

public interface CostumerRepository {
    Costumer save(Costumer costumer);
    Optional<Costumer> findById(String id);
    Optional<Costumer> findByCpf(String cpf);
    Boolean existsByCpf(Cpf cpf);
    List<Costumer> findByIdIn(List<String> ids);
}
