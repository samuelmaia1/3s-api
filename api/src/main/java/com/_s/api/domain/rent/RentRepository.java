package com._s.api.domain.rent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RentRepository {
    Rent save(Rent rent);
    Page<Rent> findAllByUserId(String userId, Pageable pageable);
    Page<Rent> findAllByCostumerId(String costumerId, Pageable pageable);
    Optional<Rent> findById(String id);
    void updateStatus(String id, RentStatus status);
    List<Rent> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);
    Integer sumReservedQuantityByProductAndPeriod(
            String productId,
            LocalDateTime deliveryDate,
            LocalDateTime returnDate,
            List<RentStatus> statuses
    );
}
