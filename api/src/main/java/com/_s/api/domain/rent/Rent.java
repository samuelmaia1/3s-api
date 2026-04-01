package com._s.api.domain.rent;

import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.rentItem.RentItem;
import com._s.api.domain.shared.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Rent {
    private String id;

    private String userId;

    private String costumerId;

    private LocalDateTime createdAt;

    private RentStatus status;

    private BigDecimal total;

    private BigDecimal deliveryTax;

    private Address deliveryAddress;

    private LocalDateTime deliveryDate;

    private LocalDateTime returnDate;

    private List<RentItem> items = new ArrayList<>();

    private Costumer costumer;

    private String code;

    public Rent(
            String userId,
            String costumerId,
            RentStatus status,
            List<RentItem> items,
            BigDecimal deliveryTax,
            Address deliveryAddress,
            LocalDateTime deliveryDate,
            LocalDateTime returnDate
    ) {
        this.userId = userId;
        this.costumerId = costumerId;
        this.status = status;
        this.items = items;
        this.deliveryTax = deliveryTax;
        this.deliveryAddress = deliveryAddress;
        this.deliveryDate = deliveryDate;
        this.returnDate = returnDate;
        this.code = generateCode();
    }

    public static Rent mount(
            String id,
            String userId,
            String costumerId,
            LocalDateTime createdAt,
            RentStatus status,
            BigDecimal total,
            BigDecimal deliveryTax,
            List<RentItem> items,
            Address deliveryAddress,
            LocalDateTime deliveryDate,
            LocalDateTime returnDate,
            Costumer costumer,
            String code
    ) {
        return new Rent(
                id,
                userId,
                costumerId,
                createdAt,
                status,
                total,
                deliveryTax,
                deliveryAddress,
                deliveryDate,
                returnDate,
                items,
                costumer,
                code
        );
    }

    private static String generateCode() {
        int code = (int) (Math.random() * 900_000) + 100_000;
        return String.valueOf(code);
    }

    public void calculateTotal() {
        BigDecimal itemsTotal = BigDecimal.ZERO;

        if (items != null && !items.isEmpty()) {
            itemsTotal = items.stream()
                    .map(RentItem::getSubTotal)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        this.total = itemsTotal.add(deliveryTax == null ? BigDecimal.ZERO : deliveryTax);
    }
}
