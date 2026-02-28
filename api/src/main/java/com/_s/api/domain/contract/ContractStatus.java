package com._s.api.domain.contract;

public enum ContractStatus {

    ASSINATURA_PENDENTE("Assinatura Pendente"),
    ASSINADO("Assinado"),
    CANCELADO("Cancelado");

    private final String label;

    ContractStatus(String label) {
        this.label = label;
    }

    @com.fasterxml.jackson.annotation.JsonValue
    public String getLabel() {
        return label;
    }
}
