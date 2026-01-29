package com._s.api.domain.user;

import com._s.api.domain.order.Order;
import com._s.api.domain.user.service.CreateUserCommand;
import com._s.api.domain.user.service.UpdateUserCommand;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    private String id;
    private String name;
    private String lastName;
    private String email;
    private String cpf;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private final List<Order> orders = new ArrayList<>();

    private User(
            String id,
            String name,
            String lastName,
            String email,
            String cpf,
            String password,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.cpf = cpf;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public User(CreateUserCommand data) {
        this.name = data.getName();
        this.lastName = data.getLastName();
        this.email = data.getEmail();
        this.cpf = data.getCpf();
        this.password = data.getPassword();
    }

    public void updateProfile(UpdateUserCommand command) {
        if (command.getName() != null) this.name = command.getName();
        if (command.getLastName() != null) this.lastName = command.getLastName();
        if (command.getEmail() != null) this.email = command.getEmail();
        if (command.getCpf() != null) this.cpf = command.getCpf();
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public static User mount(
            String id,
            String name,
            String lastName,
            String email,
            String cpf,
            String password,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            List<Order> orders
    ) {
        User user = new User(
                id, name, lastName, email, cpf, password, createdAt, updatedAt
        );

        if (orders != null) {
            orders.forEach(user::addOrder);
        }

        return user;
    }
}
