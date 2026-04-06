package com._s.api.domain.product.service;

import com._s.api.domain.order.Order;
import com._s.api.domain.order.OrderRepository;
import com._s.api.domain.order.OrderStatus;
import com._s.api.domain.product.Product;
import com._s.api.domain.rent.Rent;
import com._s.api.domain.rent.RentRepository;
import com._s.api.domain.rent.RentStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

@Service
public class GetProductDetailsService {
    private static final PageRequest RECENT_TRANSACTIONS_LIMIT = PageRequest.of(0, 5);

    private final GetProductsService getProductsService;
    private final OrderRepository orderRepository;
    private final RentRepository rentRepository;

    public GetProductDetailsService(
            GetProductsService getProductsService,
            OrderRepository orderRepository,
            RentRepository rentRepository
    ) {
        this.getProductsService = getProductsService;
        this.orderRepository = orderRepository;
        this.rentRepository = rentRepository;
    }

    public ProductDetailsData execute(String productId) {
        Product product = getProductsService.executeById(productId);

        List<Order> recentOrders = orderRepository.findRecentByProductId(productId, RECENT_TRANSACTIONS_LIMIT);
        List<Rent> recentRents = rentRepository.findRecentByProductId(productId, RECENT_TRANSACTIONS_LIMIT);

        List<ProductTransaction> recentTransactions =
                Stream.concat(
                                recentOrders.stream().map(order -> ProductTransaction.fromOrder(productId, order)),
                                recentRents.stream().map(rent -> ProductTransaction.fromRent(productId, rent))
                        )
                        .sorted(Comparator.comparing(ProductTransaction::createdAt).reversed())
                        .limit(5)
                        .toList();

        Map<YearMonth, BigDecimal> monthlyRevenue = new TreeMap<>();

        orderRepository.sumMonthlyRevenueByProductId(productId, List.of(OrderStatus.CONCLUIDO))
                .forEach(item -> monthlyRevenue.merge(
                        YearMonth.of(item.year(), item.month()),
                        item.total(),
                        BigDecimal::add
                ));

        rentRepository.sumMonthlyRevenueByProductId(productId, List.of(RentStatus.CONCLUIDO))
                .forEach(item -> monthlyRevenue.merge(
                        YearMonth.of(item.year(), item.month()),
                        item.total(),
                        BigDecimal::add
                ));

        List<MonthlyRevenueData> revenueChart = new ArrayList<>();
        monthlyRevenue.forEach((reference, total) -> revenueChart.add(new MonthlyRevenueData(
                reference.getYear(),
                reference.getMonthValue(),
                reference.toString(),
                total
        )));

        return new ProductDetailsData(product, recentTransactions, revenueChart);
    }

    public record ProductDetailsData(
            Product product,
            List<ProductTransaction> recentTransactions,
            List<MonthlyRevenueData> revenueChart
    ) {}

    public record MonthlyRevenueData(
            Integer year,
            Integer month,
            String reference,
            BigDecimal total
    ) {}

    public record ProductTransaction(
            String id,
            String code,
            TransactionType type,
            java.time.LocalDateTime createdAt,
            String status,
            BigDecimal total,
            BigDecimal productTotal,
            BigDecimal unitValue,
            Integer quantity,
            java.time.LocalDateTime deliveryDate,
            java.time.LocalDateTime returnDate,
            com._s.api.domain.costumer.Costumer costumer
    ) {
        static ProductTransaction fromOrder(String productId, Order order) {
            TransactionTotals totals = TransactionTotals.fromOrder(productId, order);

            return new ProductTransaction(
                    order.getId(),
                    order.getCode(),
                    TransactionType.ORDER,
                    order.getCreatedAt(),
                    order.getStatus().getLabel(),
                    order.getTotal(),
                    totals.productTotal(),
                    totals.unitValue(),
                    totals.quantity(),
                    order.getDeliveryDate(),
                    order.getReturnDate(),
                    order.getCostumer()
            );
        }

        static ProductTransaction fromRent(String productId, Rent rent) {
            TransactionTotals totals = TransactionTotals.fromRent(productId, rent);

            return new ProductTransaction(
                    rent.getId(),
                    rent.getCode(),
                    TransactionType.RENT,
                    rent.getCreatedAt(),
                    rent.getStatus().getLabel(),
                    rent.getTotal(),
                    totals.productTotal(),
                    totals.unitValue(),
                    totals.quantity(),
                    rent.getDeliveryDate(),
                    rent.getReturnDate(),
                    rent.getCostumer()
            );
        }
    }

    public enum TransactionType {
        ORDER,
        RENT
    }

    private record TransactionTotals(
            BigDecimal productTotal,
            BigDecimal unitValue,
            Integer quantity
    ) {
        static TransactionTotals fromOrder(String productId, Order order) {
            BigDecimal productTotal = BigDecimal.ZERO;
            BigDecimal unitValue = null;
            int quantity = 0;

            for (var item : order.getItems()) {
                if (!productId.equals(item.getProduct().getId())) {
                    continue;
                }

                productTotal = productTotal.add(item.getSubTotal());
                quantity += item.getQuantity();

                if (unitValue == null) {
                    unitValue = item.getUnitValue();
                }
            }

            return new TransactionTotals(productTotal, unitValue, quantity);
        }

        static TransactionTotals fromRent(String productId, Rent rent) {
            BigDecimal productTotal = BigDecimal.ZERO;
            BigDecimal unitValue = null;
            int quantity = 0;

            for (var item : rent.getItems()) {
                if (!productId.equals(item.getProduct().getId())) {
                    continue;
                }

                productTotal = productTotal.add(item.getSubTotal());
                quantity += item.getQuantity();

                if (unitValue == null) {
                    unitValue = item.getUnitValue();
                }
            }

            return new TransactionTotals(productTotal, unitValue, quantity);
        }
    }
}
