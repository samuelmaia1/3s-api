package com._s.api.infra.repositories.adapters;

import com._s.api.domain.refreshToken.RefreshToken;
import com._s.api.domain.refreshToken.RefreshTokenRepository;
import com._s.api.infra.mappers.RefreshTokenMapper;
import com._s.api.infra.repositories.RefreshTokenJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepository {
    private final RefreshTokenJpaRepository repository;

    public RefreshTokenRepositoryAdapter(RefreshTokenJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public RefreshToken save(RefreshToken token) {
        return RefreshTokenMapper.toDomain(repository.save(RefreshTokenMapper.toEntity(token)));
    }
}
