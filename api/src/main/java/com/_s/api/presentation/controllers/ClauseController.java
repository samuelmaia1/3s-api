package com._s.api.presentation.controllers;

import com._s.api.domain.clause.Clause;
import com._s.api.domain.clause.service.CreateClauseService;
import com._s.api.infra.security.AuthenticatedUser;
import com._s.api.presentation.dto.ClauseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clauses")
public class ClauseController {

    private final CreateClauseService createClauseService;

    @PostMapping
    public ResponseEntity<List<Clause>> create(
            @Valid @RequestBody List<ClauseRequest> data,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createClauseService.execute(data, authenticatedUser.id()));
    }
}
