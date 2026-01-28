package com._s.api.presentation.controllers;

import com._s.api.domain.product.Product;
import com._s.api.domain.product.service.CreateProductCommand;
import com._s.api.domain.product.service.CreateProductService;
import com._s.api.domain.product.service.GetProductsService;
import com._s.api.domain.user.User;
import com._s.api.domain.user.service.*;
import com._s.api.infra.security.AuthenticatedUser;
import com._s.api.presentation.dto.CreateProductRequest;
import com._s.api.presentation.dto.CreateUserRequest;
import com._s.api.presentation.dto.UpdateUserRequest;
import com._s.api.presentation.mapper.product.ProductRequestMapper;
import com._s.api.presentation.mapper.product.ProductResponseMapper;
import com._s.api.presentation.mapper.user.UserRequestMapper;
import com._s.api.presentation.mapper.user.UserResponseMapper;
import com._s.api.presentation.response.ProductResponse;
import com._s.api.presentation.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/users")
@RestController
public class UserController {

    private final CreateUserService createUserService;
    private final GetUserService getUserService;
    private final CreateProductService createProductService;
    private final GetProductsService getProductsService;
    private final UpdateUserService updateUserService;

    public UserController(CreateUserService createUserService,
                          GetUserService getUserService,
                          CreateProductService createProductService,
                          GetProductsService getProductsService,
                          UpdateUserService updateUserService
    )
    {
        this.createUserService = createUserService;
        this.getUserService = getUserService;
        this.createProductService = createProductService;
        this.getProductsService = getProductsService;
        this.updateUserService = updateUserService;
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

    @PostMapping("/products")
    public ResponseEntity<ProductResponse> createProduct(
            @Valid
            @RequestBody
            CreateProductRequest data,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ) {
        CreateProductCommand command = ProductRequestMapper.toCommand(data);

        Product product = createProductService.execute(command, authenticatedUser.id());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProductResponseMapper.toResponse(product));
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@AuthenticationPrincipal AuthenticatedUser authenticatedUser,
                                                   @Valid @RequestBody UpdateUserRequest data) {
        UpdateUserCommand command = UserRequestMapper.toUpdateCommand(data, authenticatedUser.id());

        User updatedUser = updateUserService.execute(command);

        return ResponseEntity.status(HttpStatus.OK).body(UserResponseMapper.toResponse(updatedUser));
    }

    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponse>> getProductsByUserId(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            Pageable pageable
    ) {
        getUserService.executeById(authenticatedUser.id());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(getProductsService.executeByUserId(authenticatedUser.id(), pageable).map(ProductResponseMapper::toResponse));
    }

}
