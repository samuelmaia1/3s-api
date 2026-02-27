package com._s.api.presentation.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    @Email
    private String email;

    @CPF
    private String cpf;

    private String lastName;

    private String name;

    private AddressRequest address;

    private String socialName;

    private String instagram;

    private String logo;
}
