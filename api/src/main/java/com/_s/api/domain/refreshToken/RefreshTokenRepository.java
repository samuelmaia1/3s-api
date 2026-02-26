package com._s.api.domain.refreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken token);
    Optional<RefreshToken> findById(String id);
    void delete(RefreshToken refreshToken);
}
