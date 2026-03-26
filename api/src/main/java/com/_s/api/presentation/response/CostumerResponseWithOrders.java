package com._s.api.presentation.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.shared.Address;

import lombok.Data;

@Data
public class CostumerResponseWithOrders {
    private String id;
    private String userId;
    private String name;
    private String lastName;
    private String email;
    private String cpf;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<OrderResponse> orders = new ArrayList<>();

    private Address address;

    public CostumerResponseWithOrders(Costumer costumer) {
        this.id = costumer.getId();
        this.userId = costumer.getUserId();
        this.name = costumer.getName();
        this.lastName = costumer.getLastName();
        this.email = costumer.getEmail();
        this.cpf = costumer.getCpf().getFormatted();
        this.createdAt = costumer.getCreatedAt();
        this.updatedAt = costumer.getUpdatedAt();
        this.address = costumer.getAddress();
        this.orders = costumer.getOrders().stream().map(OrderResponse::new).toList();
    }
}
