package com._s.api.infra.mappers;

import com._s.api.domain.refreshToken.RefreshToken;
import com._s.api.domain.user.User;
import com._s.api.infra.repositories.entity.RefreshTokenEntity;
import com._s.api.infra.repositories.entity.UserEntity;

public class RefreshTokenMapper {
    private RefreshTokenMapper() {}

    public static RefreshToken toDomain(RefreshTokenEntity entity) {
        if (entity == null) {
            return null;
        }

        RefreshToken token = new RefreshToken();
        token.setTokenHash(entity.getTokenHash());
        token.setId(entity.getId());
        token.setExpiresAt(entity.getExpiresAt());
        token.setUserId(entity.getUserId());
        token.setIsActive(entity.getIsActive());

        return token;
    }
    public static RefreshTokenEntity toEntity(RefreshToken token) {
        if (token == null) {
            return null;
        }

        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setTokenHash(token.getTokenHash());
        entity.setId(token.getId());
        entity.setUserId(token.getUserId());
        entity.setIsActive(token.getIsActive());
        entity.setExpiresAt(token.getExpiresAt());

        return entity;
    }

}
