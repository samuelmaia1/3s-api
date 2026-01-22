package com._s.api.presentation.dto;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String name;
    private String lastName;
    private String email;
    private String cpf;
    private String password;
}
