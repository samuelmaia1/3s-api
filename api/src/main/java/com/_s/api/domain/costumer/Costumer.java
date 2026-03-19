package com._s.api.domain.costumer;

import com._s.api.domain.costumer.service.CreateCostumerCommand;
import com._s.api.domain.order.Order;
import com._s.api.domain.shared.Address;
import com._s.api.domain.valueobject.Cpf;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Costumer {
    private String id;
    private String userId;
    private String name;
    private String lastName;
    private String email;
    private Cpf cpf;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private final List<Order> orders = new ArrayList<>();

    private Address address;

    public Costumer(CreateCostumerCommand data, String userId) {
        this.name = data.getName();
        this.lastName = data.getLastName();
        this.email = data.getEmail();
        this.cpf = new Cpf(data.getCpf());
        this.userId = userId;
        this.address = data.getAddress();
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public static Costumer mount(
            String id,
            String userId,
            String name,
            String lastName,
            String email,
            Cpf cpf,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            List<Order> orders,
            Address address
    ) {
        Costumer costumer = new Costumer(id, userId, name, lastName, email, cpf, createdAt, updatedAt, address);

        if (orders != null) {
            orders.forEach(costumer::addOrder);
        }

        return costumer;
    }
}
