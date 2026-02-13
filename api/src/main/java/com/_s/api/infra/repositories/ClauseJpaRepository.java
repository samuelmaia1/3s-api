package com._s.api.infra.repositories;

import com._s.api.infra.repositories.entity.ClauseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClauseJpaRepository extends JpaRepository<ClauseEntity, String> {
    List<ClauseEntity> findAllByUserId(String userId);
}

