package com._s.api.presentation.controllers;

import com._s.api.domain.rent.RentStatus;
import com._s.api.domain.rent.service.UpdateRentService;
import com._s.api.infra.security.AuthenticatedUser;
import com._s.api.presentation.dto.UpdateRentStatusRequest;
import com._s.api.presentation.mapper.rent.RentResponseMapper;
import com._s.api.presentation.response.RentResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/rents")
public class RentController {

    private final UpdateRentService updateRentService;

    public RentController(UpdateRentService updateRentService) {
        this.updateRentService = updateRentService;
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<RentResponse> updateRentStatus(
            @PathVariable String id,
            @Valid @RequestBody UpdateRentStatusRequest data,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(RentResponseMapper.toResponse(updateRentService.updateStatus(id, data.action(), user.id())));
    }

    @PutMapping("/finish/{id}")
    public ResponseEntity<RentResponse> finishRent(
            @PathVariable String id,
            @Valid @RequestBody UpdateRentStatusRequest data,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(RentResponseMapper.toResponse(updateRentService.updateStatus(id, RentStatus.CONCLUIDO, user.id())));
    }
}
