package com._s.api.domain.clause.service;

import com._s.api.domain.clause.Clause;
import com._s.api.domain.clause.ClauseRepository;
import com._s.api.presentation.dto.ClauseRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateClauseService {

    private final ClauseRepository repository;

    @Transactional
    public List<Clause> execute(
            List<ClauseRequest> data,
            String userId
    ) {

        List<Clause> clauses = data.stream()
            .map(clause -> new Clause(
                clause.getTitle(),
                clause.getMainText(),
                clause.getParagraphs(),
                userId
            ))
            .toList();

        return repository.saveAll(clauses);
    }
}
