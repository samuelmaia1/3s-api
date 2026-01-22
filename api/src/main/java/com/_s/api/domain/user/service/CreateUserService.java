package com._s.api.domain.user.service;

import com._s.api.domain.user.User;
import com._s.api.domain.user.UserPolicy;
import com._s.api.domain.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {
    private final UserRepository repository;
    private final UserPolicy userPolicy;

    public CreateUserService(UserRepository repository, UserPolicy userPolicy) {
        this.repository = repository;
        this.userPolicy = userPolicy;
    }

    public User execute(CreateUserCommand command) {
        userPolicy.validateEmailIsUnique(command.getEmail());

        return repository.save(new User(command));
    }
}
