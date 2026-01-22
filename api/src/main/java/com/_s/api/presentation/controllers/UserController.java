package com._s.api.presentation.controllers;

import com._s.api.domain.user.User;
import com._s.api.domain.user.service.CreateUserCommand;
import com._s.api.domain.user.service.CreateUserService;
import com._s.api.domain.user.service.GetUserService;
import com._s.api.presentation.dto.CreateUserRequest;
import com._s.api.presentation.mapper.UserRequestMapper;
import com._s.api.presentation.mapper.UserResponseMapper;
import com._s.api.presentation.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user")
@RestController
public class UserController {

    private final CreateUserService createUserService;
    private final GetUserService getUserService;

    public UserController(CreateUserService createUserService, GetUserService getUserService) {
        this.createUserService = createUserService;
        this.getUserService = getUserService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        CreateUserCommand command = UserRequestMapper.toCommand(request);

        User user = createUserService.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseMapper.toResponse(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserResponseMapper.toResponse(getUserService.executeById(id)));
    }
}
