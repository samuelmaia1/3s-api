package com._s.api.presentation.mapper.user;

import com._s.api.domain.user.service.CreateUserCommand;
import com._s.api.domain.user.service.UpdateUserCommand;
import com._s.api.presentation.dto.CreateUserRequest;
import com._s.api.presentation.dto.UpdateUserRequest;

public class UserRequestMapper {
    public static CreateUserCommand toCreateCommand(CreateUserRequest request) {
        return new CreateUserCommand(
                request.getName(),
                request.getLastName(),
                request.getEmail(),
                request.getCpf(),
                request.getPassword()
        );
    }

    public static UpdateUserCommand toUpdateCommand(UpdateUserRequest request, String id) {
        return new UpdateUserCommand(
                id,
                request.getName(),
                request.getLastName(),
                request.getEmail(),
                request.getCpf()
        );
    }
}
