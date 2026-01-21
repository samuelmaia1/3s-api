package com._s.api.domain.user;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    void save(User user);
}
