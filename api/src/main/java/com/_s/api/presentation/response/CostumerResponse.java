package com._s.api.presentation.response;

import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.order.Order;
import com._s.api.domain.shared.Address;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CostumerResponse {
    private String id;
    private String userId;
    private String name;
    private String lastName;
    private String email;
    private String cpf;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Address address;

    public CostumerResponse(Costumer costumer) {
        this.id = costumer.getId();
        this.userId = costumer.getUserId();
        this.name = costumer.getName();
        this.lastName = costumer.getLastName();
        this.email = costumer.getEmail();
        this.cpf = costumer.getCpf();
        this.createdAt = costumer.getCreatedAt();
        this.updatedAt = costumer.getUpdatedAt();
        this.address = costumer.getAddress();
    }
}
