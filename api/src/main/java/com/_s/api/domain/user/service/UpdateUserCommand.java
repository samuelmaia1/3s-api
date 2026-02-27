package com._s.api.domain.user.service;

import com._s.api.domain.shared.Address;
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
    private Address address;
    private String socialName;
    private String instagram;
    private String logo;
}
