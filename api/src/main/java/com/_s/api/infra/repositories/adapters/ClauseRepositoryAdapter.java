package com._s.api.infra.repositories.adapters;

import com._s.api.domain.clause.Clause;
import com._s.api.domain.clause.ClauseRepository;
import com._s.api.infra.mappers.ClauseMapper;
import com._s.api.infra.repositories.ClauseJpaRepository;
import com._s.api.infra.repositories.entity.ClauseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClauseRepositoryAdapter implements ClauseRepository {

    private final ClauseJpaRepository repository;

    @Override
    public Clause save(Clause clause) {
        return ClauseMapper.toDomain(
            repository.save(ClauseMapper.toEntity(clause))
        );
    }

    @Override
    public Optional<Clause> findById(String id) {
        return repository.findById(id).map(ClauseMapper::toDomain);
    }

    @Override
    public List<Clause> findAllByUserId(String userId) {
        return repository.findAllByUserId(userId)
            .stream()
            .map(ClauseMapper::toDomain)
            .toList();
    }

    @Override
    public List<Clause> saveAll(List<Clause> clauses) {

        List<ClauseEntity> entities = clauses.stream()
            .map(ClauseMapper::toEntity)
            .toList();

        return repository.saveAll(entities)
            .stream()
            .map(ClauseMapper::toDomain)
            .toList();
    }

}

