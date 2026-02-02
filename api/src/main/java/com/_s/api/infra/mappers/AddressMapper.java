package com._s.api.infra.mappers;

import com._s.api.domain.shared.Address;
import com._s.api.infra.repositories.entity.AddressEntity;

public class AddressMapper {

    public static Address toDomain(AddressEntity entity) {
        if (entity == null) return null;

        return Address.mount(
                entity.getCep(),
                entity.getStreet(),
                entity.getNeighborhood(),
                entity.getCity(),
                entity.getNumber()
        );
    }

    public static AddressEntity toEntity(Address address) {
        if (address == null) return null;

        AddressEntity entity = new AddressEntity();
        entity.setCep(address.getCep());
        entity.setStreet(address.getStreet());
        entity.setNeighborhood(address.getNeighborhood());
        entity.setCity(address.getCity());
        entity.setNumber(address.getNumber());

        return entity;
    }
}
