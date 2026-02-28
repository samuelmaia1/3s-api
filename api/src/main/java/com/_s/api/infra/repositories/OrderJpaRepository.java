package com._s.api.infra.repositories;

import com._s.api.domain.order.OrderStatus;
import com._s.api.infra.repositories.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, String> {
    Page<OrderEntity> findAllByUserId(String userId, Pageable pageable);
    Page<OrderEntity> findAllByCostumerId(String costumerId, Pageable pageable);


    @Query("""
    SELECT COUNT(o)
    FROM OrderEntity o
    WHERE o.user.id = :userId
      AND o.status IN :statuses
""")
    long countActiveOrders(
            @Param("userId") String userId,
            @Param("statuses") List<OrderStatus> statuses
    );

    @Query("""
    SELECT COALESCE(SUM(o.total), 0)
    FROM OrderEntity o
    WHERE o.user.id = :userId
      AND o.status = :status
      AND YEAR(o.createdAt) = :year
      AND MONTH(o.createdAt) = :month
""")
    BigDecimal sumMonthlyRevenue(
            @Param("userId") String userId,
            @Param("status") OrderStatus status,
            @Param("year") int year,
            @Param("month") int month
    );

    List<OrderEntity> findByUserIdOrderByCreatedAtDesc(
            String userId,
            Pageable pageable
    );
}
