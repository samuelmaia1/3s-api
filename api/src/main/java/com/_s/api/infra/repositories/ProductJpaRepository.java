package com._s.api.infra.repositories;

import com._s.api.infra.repositories.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, String> {
    Page<ProductEntity> findAllByUserId(String userId, Pageable pageable);
}
