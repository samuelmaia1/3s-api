package com._s.api.infra.repositories.entity;

import com._s.api.domain.contract.ContractReferenceType;
import com._s.api.domain.contract.ContractStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "contracts",
        uniqueConstraints = @UniqueConstraint(columnNames = {"reference_id", "reference_type"})
)
@Data
@Getter
@EntityListeners(AuditingEntityListener.class)
public class ContractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, length = 6)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private String costumerId;

    @Column(name = "reference_id", nullable = false)
    private String referenceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "reference_type", nullable = false)
    private ContractReferenceType referenceType;

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
