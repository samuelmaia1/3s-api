package com._s.api.domain.clause;

import java.util.List;
import java.util.Optional;

public interface ClauseRepository {
    Clause save(Clause clause);
    Optional<Clause> findById(String id);
    List<Clause> findAllByUserId(String userId);
    List<Clause> saveAll(List<Clause> clauses);

}
