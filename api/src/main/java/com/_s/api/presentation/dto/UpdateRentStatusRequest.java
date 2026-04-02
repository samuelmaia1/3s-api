package com._s.api.presentation.dto;

import com._s.api.domain.rent.RentStatus;

public record UpdateRentStatusRequest(RentStatus action) {
}
