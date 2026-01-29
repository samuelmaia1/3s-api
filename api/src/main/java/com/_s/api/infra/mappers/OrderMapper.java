package com._s.api.infra.mappers;

import com._s.api.domain.order.Order;
import com._s.api.domain.orderItem.OrderItem;
import com._s.api.infra.repositories.entity.OrderEntity;
import com._s.api.infra.repositories.entity.OrderItemEntity;
import com._s.api.infra.repositories.entity.UserEntity;

import java.util.List;

public class OrderMapper {

    private OrderMapper() {}

    public static Order toDomain(OrderEntity entity) {
        if (entity == null) return null;

        List<OrderItem> items = entity.getItems()
                .stream()
                .map(OrderItemMapper::toDomain)
                .toList();

        return Order.mount(
                entity.getId(),
                entity.getUser().getId(),
                entity.getCreatedAt(),
                entity.getStatus(),
                entity.getTotal(),
                items
        );
    }

    public static OrderEntity toEntity(Order order, UserEntity userEntity) {
        if (order == null) return null;

        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setStatus(order.getStatus());
        entity.setTotal(order.getTotal());
        entity.setUser(userEntity);

        List<OrderItemEntity> items = order.getItems()
            .stream()
            .map(item ->
                    OrderItemMapper.toEntity(item, entity, ProductMapper.toEntity(item.getProduct(), null))
            )
            .toList();

        entity.setItems(items);

        return entity;
    }
}

