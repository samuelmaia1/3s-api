package com._s.api.presentation.dto;

import com._s.api.infra.validation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class CreateUserRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @CPF
    private String cpf;

    @NotBlank
    @Password
    private String password;
}
