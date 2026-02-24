package com._s.api.presentation.response;

import java.math.BigDecimal;

public record DashboardSummary(
        Long activeRentals,
        BigDecimal monthlyRevenue,
        Long costumersCount,
        Long openContracts
) {}