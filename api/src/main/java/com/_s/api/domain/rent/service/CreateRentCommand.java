package com._s.api.domain.rent.service;

import com._s.api.presentation.dto.CreateRentRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRentCommand {
    private CreateRentRequest request;
}
