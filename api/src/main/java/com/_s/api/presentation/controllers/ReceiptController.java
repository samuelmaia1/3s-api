package com._s.api.presentation.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._s.api.domain.receipt.service.CreateReceiptCommand;
import com._s.api.domain.receipt.service.CreateReceiptService;
import com._s.api.infra.security.AuthenticatedUser;
import com._s.api.presentation.dto.CreatedReceipt;
import com._s.api.presentation.dto.ReceiptRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/receipts")
public class ReceiptController {
    private final CreateReceiptService createReceiptService;

    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateReceipt(
            @Valid @RequestBody ReceiptRequest request,
            @AuthenticationPrincipal AuthenticatedUser user
    ) {
        CreatedReceipt createdReceipt = createReceiptService.execute(new CreateReceiptCommand(request), user.id());
        String filename = "recibo(" + createdReceipt.referenceCode() + ").pdf";

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .body(createdReceipt.pdf());
    }
}
