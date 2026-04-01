package com._s.api.domain.contract;

import com._s.api.domain.clause.Clause;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contract {

    private String id;
    private String code;
    private String userId;
    private String costumerId;
    private String orderId;
    private ContractStatus status;
    private LocalDateTime createdAt;

    private final List<Clause> clauses = new ArrayList<>();

    public Contract(String userId, String costumerId, String orderId, List<Clause> clauses) {
        this.code = generateCode();
        this.userId = userId;
        this.costumerId = costumerId;
        this.orderId = orderId;
        this.status = ContractStatus.ASSINATURA_PENDENTE;
        this.createdAt = LocalDateTime.now();
        this.clauses.addAll(clauses);
    }

    public void markAsSigned() {
        if (this.status != ContractStatus.ASSINATURA_PENDENTE) {
            throw new IllegalStateException("Contrato não pode ser assinado.");
        }
        this.status = ContractStatus.ASSINADO;
    }

    public void markAsCanceled() {
        this.status = ContractStatus.CANCELADO;
    }

    private static String generateCode() {
        int code = (int) (Math.random() * 900_000) + 100_000;
        return String.valueOf(code);
    }

    public static Contract mount(
            String id,
            String code,
            String userId,
            String costumerId,
            String orderId,
            ContractStatus status,
            LocalDateTime createdAt,
            List<Clause> clauses
    ) {
        Contract contract = new Contract();
        contract.id = id;
        contract.code = code;
        contract.userId = userId;
        contract.costumerId = costumerId;
        contract.orderId = orderId;
        contract.status = status;
        contract.createdAt = createdAt;
        contract.clauses.addAll(clauses);
        return contract;
    }
}
