package com._s.api.domain.refreshToken.service;

import com._s.api.domain.refreshToken.RefreshToken;
import com._s.api.domain.refreshToken.RefreshTokenPolicy;
import com._s.api.domain.refreshToken.RefreshTokenRepository;
import com._s.api.domain.user.PasswordEncoder;
import com._s.api.infra.auth.InvalidTokenException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidateRefreshTokenService {
    private final RefreshTokenRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenPolicy refreshTokenPolicy;

    public ValidateRefreshTokenService(RefreshTokenRepository repository, PasswordEncoder passwordEncoder,RefreshTokenPolicy refreshTokenPolicy) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenPolicy = refreshTokenPolicy;
    }

    public RefreshToken execute(String token) {
        String[] splitToken = token.split("\\.");
        System.out.println(splitToken[0]);
        System.out.println(splitToken[1]);
        if (splitToken.length != 2) {
            throw new InvalidTokenException("Token inválido ou expirado.");
        }

        String id = splitToken[0];
        String rawRefreshToken = splitToken[1];

        Optional<RefreshToken> optionalRefreshToken = repository.findById(id);

        if (optionalRefreshToken.isEmpty())
            throw new InvalidTokenException("Token inválido ou expirado.");

        RefreshToken refreshToken = optionalRefreshToken.get();

        if (!refreshTokenPolicy.isValid(optionalRefreshToken.get()) || !passwordEncoder.matches(rawRefreshToken, refreshToken.getTokenHash()))
            throw new InvalidTokenException("Token inválido ou expirado.");

        return refreshToken;
    }
}
