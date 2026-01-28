package com._s.api.domain.user.service;

import com._s.api.domain.user.PasswordEncoder;
import com._s.api.domain.user.User;
import com._s.api.domain.user.UserPolicy;
import com._s.api.domain.user.UserRepository;
import com._s.api.domain.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateUserService {
    private final UserRepository repository;
    private final UserPolicy userPolicy;
    private final PasswordEncoder passwordEncoder;

    public UpdateUserService(UserRepository repository, UserPolicy userPolicy, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.userPolicy = userPolicy;
        this.passwordEncoder = passwordEncoder;
    }

    public User execute(UpdateUserCommand command) {
        Optional<User> optionalUser = repository.findById(command.getId());

        if (optionalUser.isEmpty())
            throw new UserNotFoundException("Usuário não encontrado.");

        User user = optionalUser.get();

        user.updateProfile(command);

        return repository.save(user);
    }
}
