package com._s.api.infra.repositories.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class AddressEntity {
    private String cep;
    private String street;
    private String neighborhood;
    private String city;
    private String number;
}
