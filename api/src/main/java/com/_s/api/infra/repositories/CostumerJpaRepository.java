package com._s.api.infra.repositories;

import com._s.api.infra.repositories.entity.CostumerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostumerJpaRepository extends JpaRepository<CostumerEntity, String> {
}
