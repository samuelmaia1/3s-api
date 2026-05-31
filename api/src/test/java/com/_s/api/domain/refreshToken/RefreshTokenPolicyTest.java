package com._s.api.domain.refreshToken;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class RefreshTokenPolicyTest {

    private RefreshTokenPolicy policy;

    @BeforeEach
    void setUp() {
        policy = new RefreshTokenPolicy();
    }

    @Test
    void deveRetornarTrueParaTokenAtivoNaoExpirado() {
        RefreshToken token = new RefreshToken(
                "id-1", "hash123", "user-1",
                Instant.now().plus(7, ChronoUnit.DAYS),
                true
        );
        assertTrue(policy.isValid(token));
    }

    @Test
    void deveRetornarFalseParaTokenInativo() {
        RefreshToken token = new RefreshToken(
                "id-2", "hash123", "user-1",
                Instant.now().plus(7, ChronoUnit.DAYS),
                false
        );
        assertFalse(policy.isValid(token));
    }

    @Test
    void deveRetornarFalseParaTokenExpirado() {
        RefreshToken token = new RefreshToken(
                "id-3", "hash123", "user-1",
                Instant.now().minus(1, ChronoUnit.SECONDS),
                true
        );
        assertFalse(policy.isValid(token));
    }

    @Test
    void deveRetornarFalseParaTokenInativoEExpirado() {
        RefreshToken token = new RefreshToken(
                "id-4", "hash123", "user-1",
                Instant.now().minus(1, ChronoUnit.DAYS),
                false
        );
        assertFalse(policy.isValid(token));
    }

    @Test
    void deveRetornarTrueParaTokenQueExpiraEmExatamente1Hora() {
        RefreshToken token = new RefreshToken(
                "id-5", "hash123", "user-1",
                Instant.now().plus(1, ChronoUnit.HOURS),
                true
        );
        assertTrue(policy.isValid(token));
    }
}
