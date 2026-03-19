package com._s.api.domain.user.service;

import com._s.api.domain.user.User;
import com._s.api.domain.user.UserRepository;
import com._s.api.domain.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateUserService {
    private final UserRepository repository;

    public UpdateUserService(UserRepository repository) {
        this.repository = repository;
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
