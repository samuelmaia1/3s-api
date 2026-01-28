package com._s.api.domain.user;

import com._s.api.domain.user.service.CreateUserCommand;
import com._s.api.domain.user.service.UpdateUserCommand;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    public void updateProfile(UpdateUserCommand command) {
        if (command.getName() != null)
            this.name = command.getName();

        if (command.getLastName() != null)
            this.lastName = command.getLastName();

        if (command.getEmail() != null)
            this.email = command.getEmail();

        if (command.getCpf() != null)
            this.cpf = command.getCpf();
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public static User mount(
            String id,
            String name,
            String lastName,
            String email,
            String cpf,
            String password,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new User(
                id, name, lastName, email, cpf, password, createdAt, updatedAt
        );
    }
}
