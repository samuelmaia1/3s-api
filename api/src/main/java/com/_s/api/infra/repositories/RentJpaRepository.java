package com._s.api.infra.repositories;

import com._s.api.domain.rent.RentStatus;
import com._s.api.infra.repositories.entity.RentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RentJpaRepository extends JpaRepository<RentEntity, String> {
    Page<RentEntity> findAllByUserId(String userId, Pageable pageable);
    Page<RentEntity> findAllByCostumerId(String costumerId, Pageable pageable);
    List<RentEntity> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);

    @Query("""
        SELECT COALESCE(SUM(ri.quantity), 0)
        FROM RentItemEntity ri
        JOIN ri.rent r
        WHERE ri.product.id = :productId
          AND r.status IN :statuses
          AND r.deliveryDate < :returnDate
          AND r.returnDate > :deliveryDate
    """)
    Integer sumReservedQuantityByProductAndPeriod(
            @Param("productId") String productId,
            @Param("deliveryDate") LocalDateTime deliveryDate,
            @Param("returnDate") LocalDateTime returnDate,
            @Param("statuses") List<RentStatus> statuses
    );

    @Modifying
    @Query("""
        UPDATE RentEntity r
        SET r.status = com._s.api.domain.rent.RentStatus.AGUARDANDO_ENTREGA
        WHERE r.deliveryDate >= :start
          AND r.deliveryDate < :end
          AND r.status IN :currentStatuses
    """)
    int updateStatusToAguardandoEntrega(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("currentStatuses") List<RentStatus> currentStatuses
    );

    @Modifying
    @Query("""
        UPDATE RentEntity r
        SET r.status = com._s.api.domain.rent.RentStatus.DEVOLUCAO_ATRASADA
        WHERE r.returnDate >= :start
          AND r.returnDate < :end
          AND r.status = :currentStatus
    """)
    int updateStatusToDevolucaoAtrasada(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("currentStatus") RentStatus currentStatus
    );
}
