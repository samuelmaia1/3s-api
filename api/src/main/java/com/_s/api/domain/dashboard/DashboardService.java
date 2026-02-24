package com._s.api.domain.dashboard;

import com._s.api.domain.contract.ContractStatus;
import com._s.api.domain.order.OrderStatus;
import com._s.api.infra.repositories.ContractJpaRepository;
import com._s.api.infra.repositories.CostumerJpaRepository;
import com._s.api.infra.repositories.OrderJpaRepository;
import com._s.api.presentation.response.DashboardSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final OrderJpaRepository orderRepository;
    private final CostumerJpaRepository costumerRepository;
    private final ContractJpaRepository contractRepository;

    public DashboardSummary getSummary(String userId) {

        LocalDate now = LocalDate.now();

        long activeRentals =
                orderRepository.countActiveOrders(
                        userId,
                        List.of(
                                OrderStatus.ENTREGUE,
                                OrderStatus.REALIZADO,
                                OrderStatus.CONTRATO_ASSINADO,
                                OrderStatus.PAGAMENTO_APROVADO
                        )
                );

        BigDecimal monthlyRevenue =
                orderRepository.sumMonthlyRevenue(
                        userId,
                        OrderStatus.CONCLUIDO,
                        now.getYear(),
                        now.getMonthValue()
                );

        long costumersCount =
                costumerRepository.countByUserId(userId);

        long openContracts =
                contractRepository.countOpenContracts(
                        List.of(
                                ContractStatus.ASSINATURA_PENDENTE
                        )
                );

        return new DashboardSummary(
                activeRentals,
                monthlyRevenue,
                costumersCount,
                openContracts
        );
    }
}
