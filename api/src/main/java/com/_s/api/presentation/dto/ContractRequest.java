package com._s.api.presentation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ContractRequest {
    @NotBlank
    private String orderId;

    @NotNull
    @Valid
    private List<ClauseRequest> clauses;

    @NotBlank
    private String costumerId;
}
