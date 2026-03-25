package com._s.api.presentation.controllers;

import com._s.api.domain.contract.service.CreateContractCommand;
import com._s.api.domain.contract.service.CreateContractService;
import com._s.api.domain.contract.service.DownloadContractService;
import com._s.api.domain.contract.service.UpdateContractService;
import com._s.api.infra.security.AuthenticatedUser;
import com._s.api.presentation.dto.ContractRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final CreateContractService createContractService;
    private final UpdateContractService updateContractService;
    private final DownloadContractService downloadContractService;

    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateContract(
            @RequestBody ContractRequest request,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        CreateContractCommand command = new CreateContractCommand(request);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contrato.pdf")
            .body(createContractService.execute(command, user.id()));
    }

    @GetMapping("/download/{code}")
    public ResponseEntity<byte[]> getMethodName(@PathVariable String code, @AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok(downloadContractService.execute(code, user.id()));
    }
    

    @PutMapping("/sign/{id}")
    private ResponseEntity<Void> signContract(@PathVariable String id) {
        updateContractService.signContract(id);
        return ResponseEntity.ok().build();
    }
}
