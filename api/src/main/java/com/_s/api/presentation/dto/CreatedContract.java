package com._s.api.presentation.dto;

import com._s.api.domain.contract.Contract;

public record CreatedContract(byte[] pdf, Contract contract) {

}
