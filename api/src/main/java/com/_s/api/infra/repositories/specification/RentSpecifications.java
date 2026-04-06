package com._s.api.infra.repositories.specification;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com._s.api.domain.rent.RentFilter;
import com._s.api.infra.repositories.entity.RentEntity;

import jakarta.persistence.criteria.Predicate;

public final class RentSpecifications {

    private RentSpecifications() {
    }

    public static Specification<RentEntity> withFilters(String userId, RentFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));

            if (filter == null) {
                return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
            }

            if (filter.status() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), filter.status()));
            }

            if (filter.createdAt() != null) {
                predicates.add(matchDate(criteriaBuilder, root.get("createdAt"), filter.createdAt()));
            }

            if (filter.deliveryDate() != null) {
                predicates.add(matchDate(criteriaBuilder, root.get("deliveryDate"), filter.deliveryDate()));
            }

            if (filter.returnDate() != null) {
                predicates.add(matchDate(criteriaBuilder, root.get("returnDate"), filter.returnDate()));
            }

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }

    private static Predicate matchDate(
            jakarta.persistence.criteria.CriteriaBuilder criteriaBuilder,
            jakarta.persistence.criteria.Path<LocalDateTime> field,
            LocalDate date
    ) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        return criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(field, start),
                criteriaBuilder.lessThan(field, end)
        );
    }
}
