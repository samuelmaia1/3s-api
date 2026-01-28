package com._s.api.domain.user.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserCommand {
    private String id;
    private String name;
    private String lastName;
    private String email;
    private String cpf;
}
