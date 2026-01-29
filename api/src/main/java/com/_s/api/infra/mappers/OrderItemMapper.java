package com._s.api.infra.mappers;

import com._s.api.domain.orderItem.OrderItem;
import com._s.api.infra.repositories.entity.OrderEntity;
import com._s.api.infra.repositories.entity.OrderItemEntity;
import com._s.api.infra.repositories.entity.ProductEntity;

public class OrderItemMapper {

    public static OrderItem toDomain(OrderItemEntity entity) {
        if (entity == null) {
            return null;
        }

        return OrderItem.mount(
            entity.getId(),
            ProductMapper.toDomain(entity.getProduct()),
            entity.getUnitValue(),
            entity.getSubTotal(),
            entity.getQuantity(),
            entity.getOrder().getId()
        );
    }

    public static OrderItemEntity toEntity(
        OrderItem item,
        OrderEntity order,
        ProductEntity product
    ) {
        if (item == null) return null;

        OrderItemEntity entity = new OrderItemEntity();
        entity.setId(item.getId());
        entity.setOrder(order);
        entity.setProduct(product);
        entity.setUnitValue(item.getUnitValue());
        entity.setQuantity(item.getQuantity());
        entity.setSubTotal(item.getSubTotal());

        return entity;
    }
}
