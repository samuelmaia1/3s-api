package com._s.api.presentation.controllers;

import com._s.api.domain.contract.service.CreateContractService;
import com._s.api.presentation.dto.ContractRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final CreateContractService createContractService;

    public ContractController(CreateContractService createContractService) {
        this.createContractService = createContractService;
    }

    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateContract( @RequestBody ContractRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contrato.pdf")
                .body(createContractService.execute(request));
    }
}
