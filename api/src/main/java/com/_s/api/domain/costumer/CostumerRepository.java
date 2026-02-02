package com._s.api.domain.costumer;

import java.util.Optional;

public interface CostumerRepository {
    Costumer save(Costumer costumer);
    Optional<Costumer> findById(String id);
    Optional<Costumer> findByCpf(String cpf);
}
