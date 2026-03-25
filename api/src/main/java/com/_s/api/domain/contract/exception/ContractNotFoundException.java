package com._s.api.domain.contract.exception;

public class ContractNotFoundException extends RuntimeException{

    public ContractNotFoundException() {
    }

    public ContractNotFoundException(String message) {
        super(message);
    }
    
}
