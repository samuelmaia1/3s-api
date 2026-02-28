package com._s.api.domain.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum OrderStatus {

    REALIZADO("Realizado"),
    AGUARDANDO_ASSINATURA_CLIENTE("Aguardando Assinatura"),
    CONTRATO_ASSINADO("Contrato Assinado"),
    AGUARDANDO_PAGAMENTO("Aguardando Pagamento"),
    PAGAMENTO_APROVADO("Pagamento Aprovado"),
    AGUARDANDO_ENTREGA("Aguardando Entrega"),
    ENTREGUE("Entregue"),
    CONCLUIDO("Concluído");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static OrderStatus fromValue(String value) {
        return Arrays.stream(values())
                .filter(status ->
                        status.name().equalsIgnoreCase(value) ||
                                status.label.equalsIgnoreCase(value)
                )
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("OrderStatus inválido: " + value)
                );
    }
}
