package com._s.api.infra.mappers;

import com._s.api.domain.rent.Rent;
import com._s.api.domain.rentItem.RentItem;
import com._s.api.infra.repositories.entity.CostumerEntity;
import com._s.api.infra.repositories.entity.RentEntity;
import com._s.api.infra.repositories.entity.RentItemEntity;
import com._s.api.infra.repositories.entity.UserEntity;

import java.util.List;

public class RentMapper {

    private RentMapper() {}

    public static Rent toDomain(RentEntity entity) {
        if (entity == null) {
            return null;
        }

        List<RentItem> items = entity.getItems()
                .stream()
                .map(RentItemMapper::toDomain)
                .toList();

        return Rent.mount(
                entity.getId(),
                entity.getUser().getId(),
                entity.getCostumer().getId(),
                entity.getCreatedAt(),
                entity.getStatus(),
                entity.getTotal(),
                entity.getDeliveryTax(),
                items,
                AddressMapper.toDomain(entity.getDeliveryAddress()),
                entity.getDeliveryDate(),
                entity.getReturnDate(),
                CostumerMapper.toDomainWithoutOrders(entity.getCostumer()),
                entity.getCode()
        );
    }

    public static RentEntity toEntity(Rent rent, UserEntity userEntity, CostumerEntity costumerEntity) {
        RentEntity entity = buildEntity(rent);

        entity.setUser(userEntity);
        entity.setCostumer(costumerEntity);
        entity.setItems(mapItems(rent, entity));

        return entity;
    }

    public static RentEntity toEntity(Rent rent, UserEntity userEntity) {
        RentEntity entity = buildEntity(rent);

        entity.setUser(userEntity);
        entity.setItems(mapItems(rent, entity));

        return entity;
    }

    public static RentEntity toEntity(Rent rent, CostumerEntity costumerEntity) {
        RentEntity entity = buildEntity(rent);

        entity.setCostumer(costumerEntity);
        entity.setItems(mapItems(rent, entity));

        return entity;
    }

    private static RentEntity buildEntity(Rent rent) {
        if (rent == null) {
            return null;
        }

        RentEntity entity = new RentEntity();
        entity.setId(rent.getId());
        entity.setStatus(rent.getStatus());
        entity.setTotal(rent.getTotal());
        entity.setDeliveryTax(rent.getDeliveryTax());
        entity.setCreatedAt(rent.getCreatedAt());
        entity.setDeliveryAddress(AddressMapper.toEntity(rent.getDeliveryAddress()));
        entity.setDeliveryDate(rent.getDeliveryDate());
        entity.setReturnDate(rent.getReturnDate());
        entity.setCode(rent.getCode());
        return entity;
    }

    private static List<RentItemEntity> mapItems(Rent rent, RentEntity entity) {
        return rent.getItems()
                .stream()
                .map(item -> RentItemMapper.toEntity(
                        item,
                        entity,
                        ProductMapper.toEntity(item.getProduct(), null)
                ))
                .toList();
    }
}
