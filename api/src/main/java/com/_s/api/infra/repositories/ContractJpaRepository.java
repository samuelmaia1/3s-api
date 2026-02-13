package com._s.api.infra.repositories;

import com._s.api.infra.repositories.entity.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractJpaRepository extends JpaRepository<ContractEntity, String> {
    Optional<ContractEntity> findByCode(String code);
}

