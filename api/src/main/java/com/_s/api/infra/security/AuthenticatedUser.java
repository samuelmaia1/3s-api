package com._s.api.infra.security;

public record AuthenticatedUser(
        String id,
        String email
) {
}
