package com._s.api.presentation.controllers;

import com._s.api.domain.costumer.service.CreateCostumerService;
import com._s.api.infra.security.AuthenticatedUser;
import com._s.api.presentation.dto.CreateCostumerRequest;
import com._s.api.presentation.mapper.costumer.CostumerRequestMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/costumers")
public class CostumerController {

    private final CreateCostumerService createCostumerService;

    public CostumerController(CreateCostumerService createCostumerService) {
        this.createCostumerService = createCostumerService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(
            @Valid @RequestBody CreateCostumerRequest data,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
            ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createCostumerService.execute(CostumerRequestMapper.toCreateCommand(data), authenticatedUser.id()));
    }
}
