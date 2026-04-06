package com._s.api.domain.receipt.service;

import com._s.api.domain.contract.ContractReferenceType;
import com._s.api.presentation.dto.ReceiptRequest;
import lombok.Getter;

@Getter
public class CreateReceiptCommand {
    private final String referenceId;
    private final ContractReferenceType referenceType;

    public CreateReceiptCommand(ReceiptRequest request) {
        this.referenceId = request.getReferenceId();
        this.referenceType = request.getReferenceType();
    }
}
