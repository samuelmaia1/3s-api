package com._s.api.domain.refreshToken.service;

import com._s.api.domain.refreshToken.RefreshToken;
import com._s.api.domain.refreshToken.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class InvalidateRefreshTokenService {
    private final RefreshTokenRepository repository;

    public InvalidateRefreshTokenService(RefreshTokenRepository repository) {
        this.repository = repository;
    }

    public void execute(String token) {
        String[] splitToken = token.split("\\.");
        repository.findById(splitToken[0]).ifPresent(repository::delete);
    }
}
