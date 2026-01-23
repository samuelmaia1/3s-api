package com._s.api.infra.repositories.adapters;

import com._s.api.domain.user.User;
import com._s.api.infra.repositories.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private UserMapper() {

    }

    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setLastName(entity.getLastName());
        user.setEmail(entity.getEmail());
        user.setCpf(entity.getCpf());
        user.setPassword(entity.getPassword());
        user.setCreatedAt(entity.getCreatedAt());
        user.setUpdatedAt(entity.getUpdatedAt());

        return user;
    }

    public static UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setLastName(user.getLastName());
        entity.setEmail(user.getEmail());
        entity.setCpf(user.getCpf());
        entity.setPassword(user.getPassword());
        entity.setCreatedAt(user.getCreatedAt());
        entity.setUpdatedAt(user.getUpdatedAt());

        return entity;
    }
}
