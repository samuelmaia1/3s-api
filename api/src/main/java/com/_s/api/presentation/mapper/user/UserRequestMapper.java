package com._s.api.presentation.mapper.user;

import com._s.api.domain.user.service.CreateUserCommand;
import com._s.api.presentation.dto.CreateUserRequest;

public class UserRequestMapper {
    public static CreateUserCommand toCommand(CreateUserRequest request) {
        return new CreateUserCommand(
                request.getName(),
                request.getLastName(),
                request.getEmail(),
                request.getCpf(),
                request.getPassword()
        );
    }
}
