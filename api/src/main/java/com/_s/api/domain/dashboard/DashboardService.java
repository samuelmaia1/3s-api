package com._s.api.domain.dashboard;

import com._s.api.domain.contract.Contract;
import com._s.api.domain.contract.ContractStatus;
import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.order.OrderStatus;
import com._s.api.infra.mappers.ContractMapper;
import com._s.api.infra.mappers.CostumerMapper;
import com._s.api.infra.mappers.OrderMapper;
import com._s.api.infra.repositories.ContractJpaRepository;
import com._s.api.infra.repositories.CostumerJpaRepository;
import com._s.api.infra.repositories.OrderJpaRepository;
import com._s.api.presentation.mapper.order.OrderResponseMapper;
import com._s.api.presentation.response.ContractResponseSummary;
import com._s.api.presentation.response.DashboardSummary;
import com._s.api.presentation.response.OrderResponseSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        var lastOrders =
                orderRepository.findByUserIdOrderByCreatedAtDesc(
                        userId,
                        PageRequest.of(0, 5)
                );

        var lastContracts =
                contractRepository.findLastContractsByUser(
                        userId,
                        PageRequest.of(0, 5)
                ).stream().map(ContractMapper::toDomain).toList();

        var costumerIds =
                Stream.concat(
                        lastOrders.stream().map(o -> o.getCostumer().getId()),
                        lastContracts.stream().map(Contract::getCostumerId)
                )
                .distinct()
                .toList();

        var costumersMap =
                costumerRepository.findByIdIn(costumerIds)
                        .stream()
                        .map(CostumerMapper::toDomain)
                        .collect(Collectors.toMap(Costumer::getId, c -> c));

        var lastOrdersSummary =
                lastOrders.stream()
                        .map(order -> {
                            var costumer =
                                    costumersMap.get(order.getCostumer().getId());

                            return new OrderResponseSummary(
                                    OrderResponseMapper.toResponse(OrderMapper.toDomain(order)),
                                    costumer
                            );
                        })
                        .toList();

        var lastContractsSummary =
                lastContracts.stream()
                        .map(contract -> {
                            var costumer =
                                    costumersMap.get(contract.getCostumerId());

                            return new ContractResponseSummary(contract, costumer);
                        })
                        .toList();

        return new DashboardSummary(
                activeRentals,
                monthlyRevenue,
                costumersCount,
                openContracts,
                lastOrdersSummary,
                lastContractsSummary
        );
    }
}
