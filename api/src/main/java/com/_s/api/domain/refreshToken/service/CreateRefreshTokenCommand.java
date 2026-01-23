package com._s.api.domain.refreshToken.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRefreshTokenCommand {
    private String userId;
}
