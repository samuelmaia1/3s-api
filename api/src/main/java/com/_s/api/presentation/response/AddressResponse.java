package com._s.api.presentation.response;

import com._s.api.domain.shared.Address;
import lombok.Data;

@Data
public class AddressResponse {
    private String cep;

    private String street;

    private String neighborhood;

    private String city;

    private String number;

    public AddressResponse(Address address) {
        if (address == null) {
            return;
        }

        this.cep = address.getCep();
        this.street = address.getStreet();
        this.neighborhood = address.getNeighborhood();
        this.city = address.getCity();
        this.number = address.getNumber();
    }
}
