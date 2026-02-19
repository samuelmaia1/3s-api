package com._s.api.infra.repositories;

import com._s.api.domain.valueobject.Cpf;
import com._s.api.infra.repositories.entity.CostumerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CostumerJpaRepository extends JpaRepository<CostumerEntity, String> {
    Optional<CostumerEntity> findByCpf(Cpf cpf);
    Boolean existsByCpf(Cpf cpf);
}
