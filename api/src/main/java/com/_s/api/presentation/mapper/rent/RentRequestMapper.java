package com._s.api.presentation.mapper.rent;

import com._s.api.domain.rent.service.CreateRentCommand;
import com._s.api.presentation.dto.CreateRentRequest;

public class RentRequestMapper {

    private RentRequestMapper() {}

    public static CreateRentCommand toCommand(CreateRentRequest request) {
        return new CreateRentCommand(request);
    }
}
