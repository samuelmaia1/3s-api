package com._s.api.presentation.controllers;

import com._s.api.domain.refreshToken.RefreshToken;
import com._s.api.domain.refreshToken.service.CreateRefreshTokenCommand;
import com._s.api.domain.refreshToken.service.CreateRefreshTokenService;
import com._s.api.domain.user.User;
import com._s.api.domain.user.service.LoginUserService;
import com._s.api.infra.security.TokenService;
import com._s.api.presentation.dto.LoginRequest;
import com._s.api.presentation.mapper.UserResponseMapper;
import com._s.api.presentation.response.LoginResponse;
import com._s.api.presentation.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final LoginUserService loginUserService;
    private final TokenService tokenService;
    private final CreateRefreshTokenService refreshTokenService;

    public AuthController(LoginUserService loginUserService, TokenService tokenService, CreateRefreshTokenService refreshTokenService) {
        this.loginUserService = loginUserService;
        this.tokenService = tokenService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest credentials) {
        User user = loginUserService.execute(credentials);

        String accessToken = tokenService.generateToken(user);

        String rawRefreshToken = refreshTokenService.execute(new CreateRefreshTokenCommand(user.getId()));

        ResponseCookie cookie = ResponseCookie.from("token", rawRefreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new LoginResponse(UserResponseMapper.toResponse(user), accessToken));
    }
}
