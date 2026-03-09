package com._s.api.infra.repositories;

import com._s.api.domain.valueobject.Cpf;
import com._s.api.infra.repositories.entity.CostumerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CostumerJpaRepository extends JpaRepository<CostumerEntity, String> {
    Optional<CostumerEntity> findByCpf(Cpf cpf);
    Boolean existsByCpf(Cpf cpf);
    Page<CostumerEntity> findAllByUserId(String userId, Pageable pageable);

    @Query("""
    SELECT COUNT(c)
    FROM CostumerEntity c
    WHERE c.user.id = :userId
""")
    long countByUserId(@Param("userId") String userId);

    List<CostumerEntity> findByIdIn(List<String> ids);

    Page<CostumerEntity> findByUserIdAndNameContainingIgnoreCase(String userId, String name, Pageable pageable);

    Boolean existsByEmail(String email);
}
