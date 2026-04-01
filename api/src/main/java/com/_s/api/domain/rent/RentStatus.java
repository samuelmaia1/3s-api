package com._s.api.domain.rent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum RentStatus {

    REALIZADO("Realizado"),
    CONTRATO_ASSINADO("Contrato Assinado"),
    PAGAMENTO_APROVADO("Pagamento Aprovado"),
    AGUARDANDO_ENTREGA("Aguardando Entrega"),
    ENTREGUE("Entregue"),
    CONCLUIDO("Concluído"),
    CANCELADO("Cancelado");

    private final String label;

    RentStatus(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static RentStatus fromValue(String value) {
        return Arrays.stream(values())
                .filter(status ->
                        status.name().equalsIgnoreCase(value) ||
                                status.label.equalsIgnoreCase(value)
                )
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Status inválido: " + value)
                );
    }
}
