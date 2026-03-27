package com._s.api.domain.clause.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com._s.api.domain.clause.Clause;
import com._s.api.domain.clause.ClauseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetClauseService {
    private final ClauseRepository repository;

    public List<Clause> getAllByUserId(String userId) {
        return repository.findAllByUserId(userId);
    }
}
