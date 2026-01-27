package com._s.api.infra.repositories.adapters;

import com._s.api.domain.product.Product;
import com._s.api.domain.product.ProductRepository;
import com._s.api.domain.user.User;
import com._s.api.domain.user.exception.UserNotFoundException;
import com._s.api.infra.mappers.ProductMapper;
import com._s.api.infra.mappers.UserMapper;
import com._s.api.infra.repositories.ProductJpaRepository;
import com._s.api.infra.repositories.UserJpaRepository;
import com._s.api.infra.repositories.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductRepositoryAdapter implements ProductRepository {

    private final ProductJpaRepository repository;
    private final UserJpaRepository userRepository;

    public ProductRepositoryAdapter(ProductJpaRepository repository, UserJpaRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public Product save(Product product, String userId) {
        Optional<UserEntity> user = userRepository.findById(userId);

        if (user.isEmpty())
            throw new UserNotFoundException("Usuário não encontrado");

        return ProductMapper.toDomain(repository.save(ProductMapper.toEntity(product, user.get())));
    }

    @Override
    public Optional<Product> findById(String id) {
        return repository.findById(id).map(ProductMapper::toDomain);
    }

    @Override
    public List<Product> findAllByUserId(String userId) {
        return repository
                .findAllByUserId(userId)
                .stream()
                .map(ProductMapper::toDomain)
                .toList();
    }
}
