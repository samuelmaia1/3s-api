package com._s.api.infra.repositories;

import com._s.api.domain.valueobject.Cpf;
import com._s.api.infra.repositories.entity.CostumerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CostumerJpaRepository extends JpaRepository<CostumerEntity, String> {
    Optional<CostumerEntity> findByCpf(Cpf cpf);
    Boolean existsByCpf(Cpf cpf);

    @Query("""
    SELECT COUNT(c)
    FROM CostumerEntity c
    WHERE c.user.id = :userId
""")
    long countByUserId(@Param("userId") String userId);

    List<CostumerEntity> findByIdIn(List<String> ids);
}
