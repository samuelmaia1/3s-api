package com._s.api.domain.user;

import com._s.api.domain.contract.Contract;
import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.order.Order;
import com._s.api.domain.shared.Address;
import com._s.api.domain.user.service.CreateUserCommand;
import com._s.api.domain.user.service.UpdateUserCommand;
import com._s.api.domain.valueobject.Cpf;
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
    private Cpf cpf;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Address address;
    private String socialName;
    private String instagram;
    private String logo;

    private final List<Order> orders = new ArrayList<>();
    private final List<Costumer> costumers = new ArrayList<>();
    private final List<Contract> contracts = new ArrayList<>();

    private User(
            String id,
            String name,
            String lastName,
            String email,
            Cpf cpf,
            String password,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Address address,
            String socialName,
            String instagram,
            String logo
    ) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.cpf = cpf;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.address = address;
        this.socialName = socialName;
        this.instagram = instagram;
        this.logo = logo;
    }


    public User(CreateUserCommand data) {
        this.name = data.getName();
        this.lastName = data.getLastName();
        this.email = data.getEmail();
        this.cpf = new Cpf(data.getCpf());
        this.password = data.getPassword();
        this.address = data.getAddress();
        this.socialName = data.getSocialName();
        this.instagram = data.getInstagram();
        this.logo = data.getLogo();
    }

    public void updateProfile(UpdateUserCommand command) {
        if (command.getName() != null) this.name = command.getName();
        if (command.getLastName() != null) this.lastName = command.getLastName();
        if (command.getEmail() != null) this.email = command.getEmail();
        if (command.getCpf() != null) this.cpf = new Cpf(command.getCpf());
        if (command.getSocialName() != null) this.socialName = command.getSocialName();
        if (command.getInstagram() != null) this.instagram = command.getInstagram();
        if (command.getLogo() != null) this.logo = command.getLogo();
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void addCostumer(Costumer costumer) { this.costumers.add(costumer); }

    public void addContract(Contract contract) { this.contracts.add(contract); }

    public static User mount(
            String id,
            String name,
            String lastName,
            String email,
            Cpf cpf,
            String password,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            List<Order> orders,
            Address address,
            List<Costumer> costumers,
            List<Contract> contracts,
            String socialName,
            String instagram,
            String logo
    ) {
        User user = new User(
                id, name, lastName, email, cpf, password, createdAt, updatedAt, address, socialName, instagram, logo
        );

        if (orders != null) {
            orders.forEach(user::addOrder);
        }

        if (costumers != null) {
            costumers.forEach(user::addCostumer);
        }

        if (contracts != null) {
            contracts.forEach(user::addContract);
        }

        return user;
    }
}
