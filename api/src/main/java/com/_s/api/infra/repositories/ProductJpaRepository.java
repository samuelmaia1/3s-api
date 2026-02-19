package com._s.api.infra.repositories;

import com._s.api.infra.repositories.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, String> {
    Page<ProductEntity> findAllByUserId(String userId, Pageable pageable);
    List<ProductEntity> findAllByIdIn(List<String> ids);
    @Modifying
    @Query("""
        UPDATE ProductEntity p
        SET p.stock = p.stock - :quantity
        WHERE p.id = :id AND p.stock >= :quantity
    """)
    int decreaseStockIfAvailable(String id, Integer quantity);

}
