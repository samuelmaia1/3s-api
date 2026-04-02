package com._s.api.domain.contract.exception;

public class ContractIllegalSignException extends RuntimeException {

    public ContractIllegalSignException(String message) {
        super(message);
    }

    public ContractIllegalSignException() {
        super("Contrato não pode ser assinado.");
    }

    
}
