package com._s.api.domain.refreshToken;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RefreshTokenPolicy {

    public boolean isValid(RefreshToken token) {
        Instant now = Instant.now();

        return token.getIsActive() && token.getExpiresAt().isAfter(now);
    }
}
