package com._s.api.infra.repositories;

import com._s.api.infra.repositories.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, String> {
    Page<OrderEntity> findAllByUserId(String userId, Pageable pageable);
}
