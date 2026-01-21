package com._s.api.domain.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private String id;

    private String name;

    private String lastName;

    private String email;

    private String cpf;

    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
