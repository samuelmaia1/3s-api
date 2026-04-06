package com._s.api.domain.order;

import java.time.LocalDate;

public record OrderFilter(
        OrderStatus status,
        LocalDate createdAt,
        LocalDate deliveryDate
) {
}
