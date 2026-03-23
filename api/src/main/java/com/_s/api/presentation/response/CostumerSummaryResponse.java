package com._s.api.presentation.response;

import com._s.api.domain.costumer.Costumer;

import lombok.Data;

@Data
public class CostumerSummaryResponse {
    private String id;
    private String name;
    private String lastName;
    private String email;

    public CostumerSummaryResponse(Costumer costumer) {
        this.id = costumer.getId();
        this.name = costumer.getName();
        this.lastName = costumer.getLastName();
        this.email = costumer.getEmail();
    }
}
