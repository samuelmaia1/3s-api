package com._s.api.domain.costumer.service;

import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.costumer.CostumerRepository;
import com._s.api.domain.costumer.exception.CostumerAlreadyExistsException;
import com._s.api.domain.user.User;
import com._s.api.domain.user.UserRepository;
import com._s.api.domain.user.exception.UserNotFoundException;
import com._s.api.domain.valueobject.Cpf;
import com._s.api.infra.mappers.CostumerMapper;
import com._s.api.infra.mappers.UserMapper;
import com._s.api.infra.repositories.entity.CostumerEntity;
import com._s.api.infra.repositories.entity.UserEntity;
import com._s.api.presentation.mapper.costumer.CostumerResponseMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CreateCostumerService {

    private final CostumerRepository repository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public CreateCostumerService(
            CostumerRepository repository,
            UserRepository userRepository,
            SimpMessagingTemplate messagingTemplate
    ) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public Costumer execute(CreateCostumerCommand command, String userId) {
        Map<String, String> errorFields = new HashMap<>();
        Boolean hasErrorFields = false;

        if (repository.existsByCpf(new Cpf(command.getCpf()))) {
            errorFields.put("cpf", "Já existe um cliente com este CPF.");
            hasErrorFields = true;
        }

        if (repository.existsByEmail(command.getEmail())) {
            errorFields.put("email", "Já existe um cliente com este e-mail.");
            hasErrorFields = true;
        }

        if (hasErrorFields) {
            throw new CostumerAlreadyExistsException("CPF já cadastrado para outro cliente.", errorFields);
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));

        UserEntity userEntity = UserMapper.toEntity(user);

        Costumer costumer = new Costumer(command, userEntity.getId());

        CostumerEntity costumerEntity = CostumerMapper.toEntity(costumer, userEntity);

        messagingTemplate.convertAndSend("/topic/costumers", CostumerResponseMapper.toResponse(costumer));

        return repository.save(CostumerMapper.toDomain(costumerEntity));
    }


}
