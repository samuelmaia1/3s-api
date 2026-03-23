package com._s.api.infra.mappers;

import com._s.api.domain.costumer.Costumer;
import com._s.api.infra.repositories.entity.CostumerEntity;
import com._s.api.infra.repositories.entity.OrderEntity;
import com._s.api.infra.repositories.entity.UserEntity;

import java.util.ArrayList;

public class CostumerMapper {
    public static Costumer toDomain(CostumerEntity entity) {
        return Costumer.mount(
                entity.getId(),
                entity.getUser().getId(),
                entity.getName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getCpf(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getOrders().stream().map(OrderMapper::toDomain).toList(),
                AddressMapper.toDomain(entity.getAddress())
        );
    }

    public static Costumer toDomainWithoutOrders(CostumerEntity entity) {
        return Costumer.mount(
                entity.getId(),
                entity.getUser().getId(),
                entity.getName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getCpf(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                null,
                AddressMapper.toDomain(entity.getAddress())
        );
    }

    public static CostumerEntity toEntity(Costumer costumer, UserEntity userEntity) {
        CostumerEntity entity = new CostumerEntity();

        entity.setId(costumer.getId());
        entity.setName(costumer.getName());
        entity.setLastName(costumer.getLastName());
        entity.setEmail(costumer.getEmail());
        entity.setCpf(costumer.getCpf());
        entity.setCreatedAt(costumer.getCreatedAt());
        entity.setUpdatedAt(costumer.getUpdatedAt());
        entity.setOrders(new ArrayList<>());
        entity.setUser(userEntity);
        entity.setAddress(AddressMapper.toEntity(costumer.getAddress()));

        costumer.getOrders().forEach(order -> {
            OrderEntity orderEntity = OrderMapper.toEntity(order, entity);
            orderEntity.setCostumer(entity);

            entity.getOrders().add(orderEntity);
        });

        return entity;
    }
}
