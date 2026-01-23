package com._s.api.domain.user.service;

import com._s.api.domain.user.PasswordEncoder;
import com._s.api.domain.user.User;
import com._s.api.domain.user.UserPolicy;
import com._s.api.domain.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {
    private final UserRepository repository;
    private final UserPolicy userPolicy;
    private final PasswordEncoder passwordEncoder;

    public CreateUserService(UserRepository repository, UserPolicy userPolicy, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.userPolicy = userPolicy;
        this.passwordEncoder = passwordEncoder;
    }

    public User execute(CreateUserCommand command) {
        userPolicy.validateEmailIsUnique(command.getEmail());
        userPolicy.validateIdentityIsUnique(command.getCpf());

        User user = new User(command);

        user.setPassword(passwordEncoder.encode(command.getPassword()));

        return repository.save(user);
    }
}
