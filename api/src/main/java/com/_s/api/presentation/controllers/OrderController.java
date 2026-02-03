package com._s.api.presentation.controllers;

import com._s.api.domain.order.service.GetOrderService;
import com._s.api.domain.order.service.UpdateOrderService;
import com._s.api.presentation.dto.UpdateStatusRequest;
import com._s.api.presentation.mapper.order.OrderResponseMapper;
import com._s.api.presentation.response.OrderResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    private final UpdateOrderService updateOrderService;
    private final GetOrderService getOrderService;

    public OrderController(UpdateOrderService updateOrderService, GetOrderService getOrderService) {
        this.updateOrderService = updateOrderService;
        this.getOrderService = getOrderService;
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable String id,
            @Valid @RequestBody UpdateStatusRequest data
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(OrderResponseMapper.toResponse(updateOrderService.updateStatus(id, data.action())));
    }
}
