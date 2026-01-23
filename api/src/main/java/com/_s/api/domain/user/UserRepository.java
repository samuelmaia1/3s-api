package com._s.api.domain.user;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    User save(User user);
    Optional<User> findById(String id);
    Optional<User> findByCpf(String cpf);
}
