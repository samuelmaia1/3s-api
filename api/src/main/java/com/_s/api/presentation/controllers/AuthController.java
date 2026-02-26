package com._s.api.presentation.controllers;

import com._s.api.domain.refreshToken.RefreshToken;
import com._s.api.domain.refreshToken.service.CreateRefreshTokenCommand;
import com._s.api.domain.refreshToken.service.CreateRefreshTokenService;
import com._s.api.domain.refreshToken.service.InvalidateRefreshTokenService;
import com._s.api.domain.refreshToken.service.ValidateRefreshTokenService;
import com._s.api.domain.user.User;
import com._s.api.domain.user.service.GetUserService;
import com._s.api.domain.user.service.LoginUserService;
import com._s.api.infra.auth.InvalidTokenException;
import com._s.api.infra.security.TokenService;
import com._s.api.presentation.dto.LoginRequest;
import com._s.api.presentation.mapper.user.UserResponseMapper;
import com._s.api.presentation.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final LoginUserService loginUserService;
    private final TokenService tokenService;
    private final CreateRefreshTokenService createRefreshTokenService;
    private final ValidateRefreshTokenService validateRefreshTokenService;
    private final InvalidateRefreshTokenService invalidateRefreshTokenService;
    private final GetUserService getUserService;

    public AuthController(LoginUserService loginUserService,
                          TokenService tokenService,
                          CreateRefreshTokenService refreshTokenService,
                          ValidateRefreshTokenService validateRefreshTokenService,
                          InvalidateRefreshTokenService invalidateRefreshTokenService,
                          GetUserService getUserService
    ) {
        this.loginUserService = loginUserService;
        this.tokenService = tokenService;
        this.createRefreshTokenService = refreshTokenService;
        this.validateRefreshTokenService = validateRefreshTokenService;
        this.invalidateRefreshTokenService = invalidateRefreshTokenService;
        this.getUserService = getUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest credentials) {
        User user = loginUserService.execute(credentials);

        String accessToken = tokenService.generateToken(user);
        String rawRefreshToken = createRefreshTokenService
                .execute(new CreateRefreshTokenCommand(user.getId()));

        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", rawRefreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .build();

        ResponseCookie accessCookie = ResponseCookie.from("access-token", accessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .body(new LoginResponse(UserResponseMapper.toResponse(user), accessToken));
    }

    @PostMapping("/validate")
    public ResponseEntity<Void> validate(HttpServletRequest request) {
        if (!tokenService.isValid(tokenService.recoverToken(request))) {
         throw new InvalidTokenException("Token inválido ou expirado");
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> generateAccessToken(
            @CookieValue("refresh_token") String refreshToken
    ) {
        RefreshToken token = validateRefreshTokenService.execute(refreshToken);

        User user = getUserService.executeById(token.getUserId());

        String accessToken = tokenService.generateToken(user);

        ResponseCookie accessCookie = ResponseCookie.from("access-token", accessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .body(new LoginResponse(UserResponseMapper.toResponse(user), accessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue("refresh_token") String refreshToken) {
        invalidateRefreshTokenService.execute(refreshToken);

        ResponseCookie cleanRefreshCookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .maxAge(0)
                .build();

        ResponseCookie cleanAccessCookie = ResponseCookie.from("access-token", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .maxAge(0)
                .build();

        return ResponseEntity
                .noContent()
                .header(HttpHeaders.SET_COOKIE, cleanRefreshCookie.toString())
                .header(HttpHeaders.SET_COOKIE, cleanAccessCookie.toString())
                .build();
    }
}
