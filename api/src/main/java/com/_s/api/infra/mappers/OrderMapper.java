package com._s.api.infra.mappers;

import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.order.Order;
import com._s.api.domain.orderItem.OrderItem;
import com._s.api.infra.repositories.entity.CostumerEntity;
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
                entity.getCostumer().getId(),
                entity.getCreatedAt(),
                entity.getStatus(),
                entity.getTotal(),
                items,
                AddressMapper.toDomain(entity.getDeliveryAddress()),
                entity.getDeliveryDate(),
                entity.getReturnDate()
        );
    }

    public static OrderEntity toEntity(Order order, UserEntity userEntity, CostumerEntity costumerEntity) {
        OrderEntity entity = buildEntity(order);

        entity.setUser(userEntity);
        entity.setCostumer(costumerEntity);

        entity.setItems(mapItems(order, entity));

        return entity;
    }

    public static OrderEntity toEntity(Order order, UserEntity userEntity) {
        OrderEntity entity = buildEntity(order);

        entity.setUser(userEntity);

        entity.setItems(mapItems(order, entity));

        return entity;
    }

    public static OrderEntity toEntity(Order order, CostumerEntity costumer) {
        OrderEntity entity = buildEntity(order);

        entity.setCostumer(costumer);

        entity.setItems(mapItems(order, entity));

        return entity;
    }

    private static OrderEntity buildEntity(Order order) {
        if (order == null) return null;

        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setStatus(order.getStatus());
        entity.setTotal(order.getTotal());
        entity.setCreatedAt(order.getCreatedAt());
        entity.setDeliveryAddress(AddressMapper.toEntity(order.getDeliveryAddress()));
        entity.setDeliveryDate(order.getDeliveryDate());
        entity.setReturnDate(order.getReturnDate());
        return entity;
    }

    private static List<OrderItemEntity> mapItems(Order order, OrderEntity entity) {
        return order.getItems()
                .stream()
                .map(item ->
                        OrderItemMapper.toEntity(item, entity, ProductMapper.toEntity(item.getProduct(), null))
                )
                .toList();
    }
}

