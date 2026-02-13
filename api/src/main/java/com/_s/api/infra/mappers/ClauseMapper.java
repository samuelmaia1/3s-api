package com._s.api.infra.mappers;

import com._s.api.domain.clause.Clause;
import com._s.api.infra.repositories.entity.ClauseEntity;

import java.util.ArrayList;

public class ClauseMapper {

    private ClauseMapper() {}

    public static Clause toDomain(ClauseEntity entity) {
        if (entity == null) {
            return null;
        }

        return Clause.mount(
                entity.getId(),
                entity.getTitle(),
                entity.getMainText(),
                entity.getParagraphs(),
                entity.getUserId(),
                entity.getCreatedAt()
        );
    }

    public static ClauseEntity toEntity(Clause clause) {
        if (clause == null) {
            return null;
        }

        ClauseEntity entity = new ClauseEntity();

        entity.setId(clause.getId());
        entity.setTitle(clause.getTitle());
        entity.setMainText(clause.getMainText());
        entity.setParagraphs(
                clause.getParagraphs() != null
                        ? new ArrayList<>(clause.getParagraphs())
                        : new ArrayList<>()
        );
        entity.setUserId(clause.getUserId());
        entity.setCreatedAt(clause.getCreatedAt());

        // Não seta contracts aqui para evitar loop e manter aggregate limpo
        entity.setContracts(new ArrayList<>());

        return entity;
    }
}
