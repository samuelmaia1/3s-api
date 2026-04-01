package com._s.api.infra.mappers;

import com._s.api.domain.rentItem.RentItem;
import com._s.api.infra.repositories.entity.ProductEntity;
import com._s.api.infra.repositories.entity.RentEntity;
import com._s.api.infra.repositories.entity.RentItemEntity;

public class RentItemMapper {

    private RentItemMapper() {}

    public static RentItem toDomain(RentItemEntity entity) {
        if (entity == null) {
            return null;
        }

        return RentItem.mount(
                entity.getId(),
                ProductMapper.toDomain(entity.getProduct()),
                entity.getUnitValue(),
                entity.getSubTotal(),
                entity.getQuantity(),
                entity.getRent().getId()
        );
    }

    public static RentItemEntity toEntity(
            RentItem item,
            RentEntity rent,
            ProductEntity product
    ) {
        if (item == null) {
            return null;
        }

        RentItemEntity entity = new RentItemEntity();
        entity.setId(item.getId());
        entity.setRent(rent);
        entity.setProduct(product);
        entity.setUnitValue(item.getUnitValue());
        entity.setQuantity(item.getQuantity());
        entity.setSubTotal(item.getSubTotal());

        return entity;
    }
}
