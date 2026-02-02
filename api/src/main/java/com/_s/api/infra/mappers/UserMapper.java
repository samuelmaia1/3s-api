package com._s.api.infra.mappers;

import com._s.api.domain.user.User;
import com._s.api.infra.repositories.entity.OrderEntity;
import com._s.api.infra.repositories.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserMapper {

    private UserMapper() {

    }

    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return User.mount(
            entity.getId(),
            entity.getName(),
            entity.getLastName(),
            entity.getEmail(),
            entity.getCpf(),
            entity.getPassword(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getOrders().stream().map(OrderMapper::toDomain).toList(),
            AddressMapper.toDomain(entity.getAddress()),
            entity.getCostumers().stream().map(CostumerMapper::toDomain).toList()
        );
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
        entity.setAddress(AddressMapper.toEntity(user.getAddress()));
        entity.setOrders(new ArrayList<>());

        user.getOrders().forEach(order -> {
            OrderEntity orderEntity = OrderMapper.toEntity(order, entity);
            orderEntity.setUser(entity);

            entity.getOrders().add(orderEntity);
        });

        return entity;
    }
}
