package com._s.api.domain.refreshToken;

import com._s.api.domain.refreshToken.service.CreateRefreshTokenCommand;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String tokenHash;

    private String userId;

    private Instant expiresAt;

    private Boolean isActive;

    public RefreshToken(CreateRefreshTokenCommand command) {
        this.userId = command.getUserId();
        this.isActive = true;
    }
}
