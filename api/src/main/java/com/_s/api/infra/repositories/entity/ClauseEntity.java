package com._s.api.infra.repositories.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "clauses")
@Data
@Getter
@EntityListeners(AuditingEntityListener.class)
public class ClauseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String mainText;

    @ElementCollection
    @CollectionTable(name = "clause_paragraphs", joinColumns = @JoinColumn(name = "clause_id"))
    @Column(name = "paragraph")
    private List<String> paragraphs;

    private String userId;

    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "clauses")
    private List<ContractEntity> contracts;
}
