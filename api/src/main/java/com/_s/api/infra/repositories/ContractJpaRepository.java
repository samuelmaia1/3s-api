package com._s.api.infra.repositories;

import com._s.api.domain.contract.ContractStatus;
import com._s.api.infra.repositories.entity.ContractEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractJpaRepository extends JpaRepository<ContractEntity, String> {
    Optional<ContractEntity> findByCode(String code);
    List<ContractEntity> findAllByUserIdOrderByCreatedAtDesc(String userId);

    @Query("""
    SELECT COUNT(c)
    FROM ContractEntity c
    WHERE c.status IN :statuses
""")
    long countOpenContracts(@Param("statuses") List<ContractStatus> statuses);

    @Query("""
    SELECT c
    FROM ContractEntity c
    WHERE c.user.id = :userId
    ORDER BY c.createdAt DESC
""")
    List<ContractEntity> findLastContractsByUser(
            @Param("userId") String userId,
            Pageable pageable
    );

    Boolean existsByOrderId(String orderId);

    void delete(ContractEntity contract);

    Optional<ContractEntity> findByOrderId(String orderId);

    void deleteByOrderId(String orderId);
}
