package com._s.api.presentation.mapper.contract;

import com._s.api.domain.contract.service.CreateContractCommand;
import com._s.api.presentation.dto.ContractRequest;

public class ContractRequestMapper {
    public static CreateContractCommand toCreateCommand(ContractRequest request) {
        return new CreateContractCommand(request);
    }
}
