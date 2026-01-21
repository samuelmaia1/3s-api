package com._s.api.infra.repositories.adapters;

import com._s.api.domain.user.User;
import com._s.api.domain.user.UserRepository;
import com._s.api.infra.repositories.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepository {

    @Autowired
    private UserJpaRepository repository;

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void save(User user) {

    }
}
