package com._s.api.infra.repositories;

import com._s.api.domain.order.OrderStatus;
import com._s.api.infra.repositories.entity.OrderEntity;
import com._s.api.infra.repositories.projection.MonthlyRevenueProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, String>, JpaSpecificationExecutor<OrderEntity> {
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

    @Query("""
    SELECT DISTINCT o
    FROM OrderEntity o
    JOIN o.items oi
    WHERE oi.product.id = :productId
    ORDER BY o.createdAt DESC
""")
    List<OrderEntity> findRecentByProductId(
            @Param("productId") String productId,
            Pageable pageable
    );

    @Query("""
    SELECT
      YEAR(o.createdAt) AS year,
      MONTH(o.createdAt) AS month,
      COALESCE(SUM(oi.subTotal), 0) AS total
    FROM OrderItemEntity oi
    JOIN oi.order o
    WHERE oi.product.id = :productId
      AND o.status IN :statuses
    GROUP BY YEAR(o.createdAt), MONTH(o.createdAt)
    ORDER BY YEAR(o.createdAt), MONTH(o.createdAt)
""")
    List<MonthlyRevenueProjection> sumMonthlyRevenueByProductId(
            @Param("productId") String productId,
            @Param("statuses") List<OrderStatus> statuses
    );
}
