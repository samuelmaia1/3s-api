package com._s.api.domain.user.service;

import com._s.api.domain.user.PasswordEncoder;
import com._s.api.domain.user.User;
import com._s.api.domain.user.UserPolicy;
import com._s.api.domain.user.UserRepository;
import com._s.api.domain.user.exception.UserNotFoundException;
import com._s.api.infra.auth.InvalidCredentialsException;
import com._s.api.presentation.dto.LoginRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginUserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public LoginUserService(UserRepository repository, UserPolicy userPolicy, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User execute(LoginRequest credentials) {
        Optional<User> optionalUser = this.repository.findByEmail(credentials.getEmail());

        if (optionalUser.isEmpty()) throw new UserNotFoundException("Não existe usuário com este e-mail. Por favor, verifique.");

        User user = optionalUser.get();

        if (!this.passwordEncoder.matches(credentials.getPassword(), user.getPassword())) throw new InvalidCredentialsException("Credenciais incorretas.");

        return user;
    }
}
