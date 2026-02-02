package com._s.api.domain.costumer.service;

import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.costumer.CostumerRepository;
import com._s.api.domain.user.User;
import com._s.api.domain.user.UserRepository;
import com._s.api.domain.user.exception.UserNotFoundException;
import com._s.api.infra.mappers.CostumerMapper;
import com._s.api.infra.mappers.UserMapper;
import com._s.api.infra.repositories.entity.CostumerEntity;
import com._s.api.infra.repositories.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateCostumerService {

    private final CostumerRepository repository;
    private final UserRepository userRepository;

    public CreateCostumerService(CostumerRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Costumer execute(CreateCostumerCommand command, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));

        UserEntity userEntity = UserMapper.toEntity(user);

        Costumer costumer = new Costumer(command, userEntity.getId());

        CostumerEntity costumerEntity = CostumerMapper.toEntity(costumer, userEntity);

        return repository.save(CostumerMapper.toDomain(costumerEntity));
    }
}
