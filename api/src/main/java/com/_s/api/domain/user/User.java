package com._s.api.domain.user;

import com._s.api.domain.user.service.CreateUserCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;

    private String name;

    private String lastName;

    private String email;

    private String cpf;

    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public User(CreateUserCommand data) {
        this.name = data.getName();
        this.lastName = data.getLastName();
        this.email = data.getEmail();
        this.cpf = data.getCpf();
        this.password = data.getPassword();
    }
}
