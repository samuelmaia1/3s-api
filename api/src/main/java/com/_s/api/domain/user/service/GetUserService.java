package com._s.api.domain.user.service;

import com._s.api.domain.user.User;
import com._s.api.domain.user.UserRepository;
import com._s.api.domain.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetUserService {
    private final UserRepository repository;

    public GetUserService(UserRepository repository) {
        this.repository = repository;
    }

    public User executeById(String id) {
        System.out.println(id);
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário com este Id não encontrado"));
    }
}
