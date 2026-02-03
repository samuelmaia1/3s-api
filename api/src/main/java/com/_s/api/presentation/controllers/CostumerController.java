package com._s.api.presentation.controllers;

import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.costumer.service.CreateCostumerService;
import com._s.api.domain.costumer.service.GetCostumerService;
import com._s.api.domain.order.service.GetOrderService;
import com._s.api.infra.security.AuthenticatedUser;
import com._s.api.presentation.dto.CreateCostumerRequest;
import com._s.api.presentation.mapper.costumer.CostumerRequestMapper;
import com._s.api.presentation.mapper.costumer.CostumerResponseMapper;
import com._s.api.presentation.mapper.order.OrderResponseMapper;
import com._s.api.presentation.response.OrderResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/costumers")
public class CostumerController {

    private final CreateCostumerService createCostumerService;
    private final GetCostumerService getCostumerService;
    private final GetOrderService getOrderService;

    public CostumerController(
            CreateCostumerService createCostumerService,
            GetCostumerService getCostumerService,
            GetOrderService getOrderService
    ) {
        this.createCostumerService = createCostumerService;
        this.getCostumerService = getCostumerService;
        this.getOrderService = getOrderService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(
            @Valid @RequestBody CreateCostumerRequest data,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
            ) {
        Costumer costumer = createCostumerService.execute(CostumerRequestMapper.toCreateCommand(data), authenticatedUser.id());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CostumerResponseMapper.toResponse(costumer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCostumerById(@PathVariable String id) {
        Costumer costumer = getCostumerService.execute(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CostumerResponseMapper.toResponse(costumer));
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<Page<OrderResponse>> getOrdersByCostumerId(
            @PathVariable String id,
            Pageable pageable,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort
    ) {
        validateCostumerExists(id);

        Pageable resolvedPageable;

        if (page == null && size == null && sort == null) {
            resolvedPageable = PageRequest.of(
                    0,
                    10,
                    Sort.by("createdAt").descending()
            );
        } else {
            resolvedPageable = pageable;
        }

        return ResponseEntity.ok(
                getOrderService.executeByCostumerId(id, resolvedPageable)
                        .map(OrderResponseMapper::toResponse)
        );
    }

    private void validateCostumerExists(String id) {
        getCostumerService.execute(id);
    }
}
