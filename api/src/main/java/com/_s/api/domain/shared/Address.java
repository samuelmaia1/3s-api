package com._s.api.domain.shared;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Address {
    private String cep;
    private String street;
    private String neighborhood;
    private String city;
    private String number;

    public static Address mount(String cep, String street, String neighborhood, String city, String number) {
        return new Address(
                cep,
                street,
                neighborhood, city,
                number
        );
    }
}
