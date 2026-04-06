package com._s.api.presentation.dto;

import com._s.api.domain.contract.ContractReferenceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReceiptRequest {
    @NotBlank
    private String referenceId;

    @NotNull
    private ContractReferenceType referenceType;
}
