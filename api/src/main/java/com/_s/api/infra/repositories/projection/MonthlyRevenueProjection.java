package com._s.api.infra.repositories.projection;

import java.math.BigDecimal;

public interface MonthlyRevenueProjection {
    Integer getYear();
    Integer getMonth();
    BigDecimal getTotal();
}
