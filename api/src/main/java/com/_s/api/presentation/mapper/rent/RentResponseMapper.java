package com._s.api.presentation.mapper.rent;

import com._s.api.domain.rent.Rent;
import com._s.api.presentation.response.RentResponse;

public class RentResponseMapper {
    public static RentResponse toResponse(Rent rent) {
        return new RentResponse(rent);
    }
}
