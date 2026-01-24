package com._s.api.domain.user;

import com._s.api.domain.user.exception.EmailAlreadyInUseException;
import com._s.api.domain.user.exception.IdentityAlreadyInUseException;
import org.springframework.stereotype.Component;

@Component
public class UserPolicy {
    private final UserRepository repository;

    public UserPolicy(UserRepository repository) {
        this.repository = repository;
    }

    public void validateEmailIsUnique(String email) {
        if (repository.findByEmail(email).isPresent())
            throw new EmailAlreadyInUseException("O e-mail " + email + " já está em uso.");
    }

    public void validateIdentityIsUnique(String cpf) {
        if (repository.findByCpf(cpf).isPresent())
            throw new IdentityAlreadyInUseException("O cpf " + cpf + " já está em uso.");
    }
}
