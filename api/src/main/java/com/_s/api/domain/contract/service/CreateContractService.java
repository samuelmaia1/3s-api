package com._s.api.domain.contract.service;

import com._s.api.presentation.dto.ContractRequest;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;

@Service
public class CreateContractService {

    private final SpringTemplateEngine engine;

    public CreateContractService(SpringTemplateEngine engine) {
        this.engine = engine;
    }

    public byte[] execute(ContractRequest data) {
        Context context = new Context();

        context.setVariable("order", data.getOrder());
        context.setVariable("clauses", data.getClauses());

        String html = engine.process("contract", context);

        return generatePdfFromHtml(html);
    }

    private byte[] generatePdfFromHtml(String html) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null);
            builder.toStream(outputStream);
            builder.run();

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar contrato em PDF", e);
        }
    }
}
