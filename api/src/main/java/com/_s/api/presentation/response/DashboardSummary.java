package com._s.api.presentation.response;


import java.math.BigDecimal;
import java.util.List;

public record DashboardSummary(
        Long activeRentals,
        BigDecimal monthlyRevenue,
        Long costumersCount,
        Long openContracts,
        List<OrderResponseSummary> lastOrders,
        List<ContractResponseSummary> lastContracts
) {}