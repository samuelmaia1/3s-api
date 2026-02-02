package com._s.api.presentation.mapper.costumer;

import com._s.api.domain.costumer.service.CreateCostumerCommand;
import com._s.api.presentation.dto.CreateCostumerRequest;
import com._s.api.presentation.mapper.shared.AddressRequestMapper;

public class CostumerRequestMapper {
    public static CreateCostumerCommand toCreateCommand(CreateCostumerRequest request) {
        return new CreateCostumerCommand(
                request.getName(),
                request.getLastName(),
                request.getEmail(),
                request.getCpf(),
                AddressRequestMapper.toDomain(request.getAddress())
        );
    }
}
