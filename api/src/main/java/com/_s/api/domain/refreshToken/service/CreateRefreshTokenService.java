package com._s.api.domain.refreshToken.service;

import com._s.api.domain.refreshToken.RefreshToken;
import com._s.api.domain.refreshToken.RefreshTokenRepository;
import com._s.api.domain.user.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class CreateRefreshTokenService {

    private final RefreshTokenRepository repository;
    private final PasswordEncoder passwordEncoder;

    public CreateRefreshTokenService(RefreshTokenRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public String execute(CreateRefreshTokenCommand command) {
        String rawRefreshToken = UUID.randomUUID().toString();
        String hashedToken = passwordEncoder.encode(rawRefreshToken);

        RefreshToken token = new RefreshToken(command);

        token.setExpiresAt(generateExpireDate());
        token.setTokenHash(hashedToken);

        RefreshToken savedRefreshToken = repository.save(token);

        return savedRefreshToken.getId() + "." + rawRefreshToken;
    }

    private Instant generateExpireDate() {
        return LocalDateTime.now().plusMinutes(60 * 24 * 7).toInstant(ZoneOffset.of("-03:00"));
    }
}
