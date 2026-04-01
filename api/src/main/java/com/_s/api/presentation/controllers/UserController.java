package com._s.api.presentation.controllers;

import com._s.api.domain.contract.service.GetContractService;
import com._s.api.domain.costumer.service.GetCostumerService;
import com._s.api.domain.order.service.CreateOrderCommand;
import com._s.api.domain.order.service.CreateOrderService;
import com._s.api.domain.order.service.GetOrderService;
import com._s.api.domain.product.Product;
import com._s.api.domain.product.service.CreateProductCommand;
import com._s.api.domain.product.service.CreateProductService;
import com._s.api.domain.product.service.GetProductsService;
import com._s.api.domain.rent.service.CreateRentCommand;
import com._s.api.domain.rent.service.CreateRentService;
import com._s.api.domain.rent.service.GetRentService;
import com._s.api.domain.user.User;
import com._s.api.domain.user.service.*;
import com._s.api.infra.security.AuthenticatedUser;
import com._s.api.presentation.dto.CreateOrderRequest;
import com._s.api.presentation.dto.CreateProductRequest;
import com._s.api.presentation.dto.CreateRentRequest;
import com._s.api.presentation.dto.CreateUserRequest;
import com._s.api.presentation.dto.UpdateUserRequest;
import com._s.api.presentation.mapper.costumer.CostumerResponseMapper;
import com._s.api.presentation.mapper.order.OrderResponseMapper;
import com._s.api.presentation.mapper.product.ProductRequestMapper;
import com._s.api.presentation.mapper.product.ProductResponseMapper;
import com._s.api.presentation.mapper.rent.RentRequestMapper;
import com._s.api.presentation.mapper.rent.RentResponseMapper;
import com._s.api.presentation.mapper.user.UserRequestMapper;
import com._s.api.presentation.mapper.user.UserResponseMapper;
import com._s.api.presentation.response.ContractResponseSummary;
import com._s.api.presentation.response.CostumerResponse;
import com._s.api.presentation.response.OrderResponse;
import com._s.api.presentation.response.ProductResponse;
import com._s.api.presentation.response.RentResponse;
import com._s.api.presentation.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@RestController
public class UserController {

    private final CreateUserService createUserService;
    private final GetUserService getUserService;
    private final CreateProductService createProductService;
    private final GetProductsService getProductsService;
    private final UpdateUserService updateUserService;
    private final GetOrderService getOrderService;
    private final CreateOrderService createOrderService;
    private final CreateRentService createRentService;
    private final GetCostumerService getCostumerService;
    private final GetContractService getContractService;
    private final GetRentService getRentService;

    public UserController(
        CreateUserService createUserService,
        GetUserService getUserService,
        CreateProductService createProductService,
        GetProductsService getProductsService,
        UpdateUserService updateUserService,
        GetOrderService getOrderService,
        CreateOrderService createOrderService,
        CreateRentService createRentService,
        GetCostumerService getCostumerService,
        GetContractService getContractService,
        GetRentService getRentService
    )
    {
        this.createUserService = createUserService;
        this.getUserService = getUserService;
        this.createProductService = createProductService;
        this.getProductsService = getProductsService;
        this.updateUserService = updateUserService;
        this.getOrderService = getOrderService;
        this.createOrderService = createOrderService;
        this.createRentService = createRentService;
        this.getCostumerService = getCostumerService;
        this.getContractService = getContractService;
        this.getRentService = getRentService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(
            @Valid
            @RequestBody
            CreateUserRequest request
    ) {
        CreateUserCommand command = UserRequestMapper.toCreateCommand(request);

        User user = createUserService.execute(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponseMapper.toResponse(user));
    }

    @GetMapping
    public ResponseEntity<UserResponse> getUserById(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserResponseMapper.toResponse(getUserService.executeById(authenticatedUser.id())));
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@AuthenticationPrincipal AuthenticatedUser authenticatedUser,
                                                   @Valid @RequestBody UpdateUserRequest data) {
        validateUserExists(authenticatedUser.id());

        UpdateUserCommand command = UserRequestMapper.toUpdateCommand(data, authenticatedUser.id());

        User updatedUser = updateUserService.execute(command);

        return ResponseEntity.status(HttpStatus.OK).body(UserResponseMapper.toResponse(updatedUser));
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponse> createProduct(
            @Valid
            @RequestBody
            CreateProductRequest data,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ) {
        validateUserExists(authenticatedUser.id());

        CreateProductCommand command = ProductRequestMapper.toCommand(data);

        Product product = createProductService.execute(command, authenticatedUser.id());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProductResponseMapper.toResponse(product));
    }


    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponse>> getProductsByUserId(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable,
            @RequestParam(required = false) String name
    ) {

        validateUserExists(authenticatedUser.id());

        Page<ProductResponse> response = getProductsService
                .executeByUserId(authenticatedUser.id(), name, pageable)
                .map(ProductResponseMapper::toResponse);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @Valid @RequestBody CreateOrderRequest request
            ) {
        validateUserExists(authenticatedUser.id());

        CreateOrderCommand command = new CreateOrderCommand(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(OrderResponseMapper.toResponse(createOrderService.execute(command, authenticatedUser.id())));
    }

    @PostMapping("/rents")
    public ResponseEntity<RentResponse> createRent(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @Valid @RequestBody CreateRentRequest request
    ) {
        validateUserExists(authenticatedUser.id());

        CreateRentCommand command = RentRequestMapper.toCommand(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(RentResponseMapper.toResponse(createRentService.execute(command, authenticatedUser.id())));
    }

    @GetMapping("/orders")
    public ResponseEntity<Page<OrderResponse>> getOrdersByUserId(
        @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
        Pageable pageable,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size,
        @RequestParam(required = false) String sort
    ) {
        validateUserExists(authenticatedUser.id());

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

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getOrderService.executeByUserId(authenticatedUser.id(), resolvedPageable).map(OrderResponseMapper::toResponse));
    }

    @GetMapping("/rents")
    public ResponseEntity<Page<RentResponse>> getRentsByUserId(
        @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
        Pageable pageable,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size,
        @RequestParam(required = false) String sort
    ) {
        validateUserExists(authenticatedUser.id());

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

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getRentService.executeByUserId(authenticatedUser.id(), resolvedPageable).map(RentResponseMapper::toResponse));
    }

    @GetMapping("/costumers")
    public ResponseEntity<Page<CostumerResponse>> getCostumersByUserId(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable,
            @RequestParam(name = "name", required = false) String name
    ) {
        validateUserExists(authenticatedUser.id());


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getCostumerService.executeByUserId(authenticatedUser.id(), name, pageable).map(CostumerResponseMapper::toResponse));
    }

    @GetMapping("/contracts")
    public ResponseEntity<Page<ContractResponseSummary>> getContractsByUserId(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {
        validateUserExists(authenticatedUser.id());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(getContractService.executeByUserId(authenticatedUser.id(), pageable));
    }


    private void validateUserExists(String id) {
        getUserService.executeById(id);
    }
}
