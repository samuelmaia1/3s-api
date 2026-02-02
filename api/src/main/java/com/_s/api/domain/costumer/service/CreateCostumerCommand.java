package com._s.api.domain.costumer.service;

import com._s.api.domain.shared.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCostumerCommand {
    private String name;
    private String lastName;
    private String email;
    private String cpf;
    private Address address;
}
