package com._s.api.presentation.mapper.shared;

import com._s.api.domain.shared.Address;
import com._s.api.presentation.dto.AddressRequest;

public class AddressRequestMapper {
    public static Address toDomain(AddressRequest request) {
        if (request == null) return null;

        return Address.mount(
                request.getCep(),
                request.getStreet(),
                request.getNeighborhood(),
                request.getCity(),
                request.getNumber()
        );
    }
}
