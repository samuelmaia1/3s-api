package com._s.api.infra.repositories.entity;

import com._s.api.domain.contract.ContractStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contracts")
@Data
@Getter
@EntityListeners(AuditingEntityListener.class)
public class ContractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, length = 6)
    private String code;

    private String costumerId;
    private String orderId;

    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "contract_clauses",
            joinColumns = @JoinColumn(name = "contract_id"),
            inverseJoinColumns = @JoinColumn(name = "clause_id")
    )
    private List<ClauseEntity> clauses = new ArrayList<>();
}

