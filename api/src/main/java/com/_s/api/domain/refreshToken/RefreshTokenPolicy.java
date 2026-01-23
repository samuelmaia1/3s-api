package com._s.api.domain.refreshToken;

import java.time.Instant;

public class RefreshTokenPolicy {

    public boolean isValid(RefreshToken token) {
        Instant now = Instant.now();

        return token.getIsActive() && token.getExpiresAt().isAfter(now);
    }
}
