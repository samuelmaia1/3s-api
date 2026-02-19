package com._s.api.infra.repositories.adapters;

import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.costumer.CostumerRepository;
import com._s.api.domain.user.exception.UserNotFoundException;
import com._s.api.domain.valueobject.Cpf;
import com._s.api.infra.mappers.CostumerMapper;
import com._s.api.infra.repositories.CostumerJpaRepository;
import com._s.api.infra.repositories.UserJpaRepository;
import com._s.api.infra.repositories.entity.CostumerEntity;
import com._s.api.infra.repositories.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CostumerRepositoryAdapter implements CostumerRepository {

    private final CostumerJpaRepository repository;
    private final UserJpaRepository userRepository;

    public CostumerRepositoryAdapter(CostumerJpaRepository repository, UserJpaRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public Costumer save(Costumer costumer) {
        UserEntity userRef = userRepository.getReferenceById(costumer.getUserId());

        return CostumerMapper.toDomain(repository.save(CostumerMapper.toEntity(costumer, userRef)));
    }

    @Override
    public Optional<Costumer> findById(String id) {
        return repository.findById(id).map(CostumerMapper::toDomain);
    }

    @Override
    public Optional<Costumer> findByCpf(String cpf) {
        return repository.findByCpf(new Cpf(cpf)).map(CostumerMapper::toDomain);
    }

    @Override
    public Boolean existsByCpf(Cpf cpf) {
        return repository.existsByCpf(cpf);
    }
}
