package com._s.api.domain.rent;

import java.time.LocalDate;

public record RentFilter(
        RentStatus status,
        LocalDate createdAt,
        LocalDate deliveryDate,
        LocalDate returnDate
) {
}
