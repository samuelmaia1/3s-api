package com._s.api.domain.user.service;

import com._s.api.domain.shared.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserCommand {
    private String name;
    private String lastName;
    private String email;
    private String cpf;
    private String password;
    private Address address;
    private String socialName;
    private String instagram;
}
