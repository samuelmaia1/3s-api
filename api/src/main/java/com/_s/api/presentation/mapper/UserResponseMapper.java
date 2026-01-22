package com._s.api.presentation.mapper;

import com._s.api.domain.user.User;
import com._s.api.presentation.response.UserResponse;

public class UserResponseMapper {
    public static UserResponse toResponse(User user) {
        return new UserResponse(user);
    }
}
