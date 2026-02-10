package com._s.api.domain.contract.service;

import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.costumer.service.GetCostumerService;
import com._s.api.domain.order.Order;
import com._s.api.domain.order.service.GetOrderService;
import com._s.api.infra.mappers.OrderMapper;
import com._s.api.presentation.dto.ContractRequest;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class CreateContractService {

    private final SpringTemplateEngine engine;
    private final GetOrderService getOrderService;
    private final GetCostumerService getCostumerService;

    public byte[] execute(ContractRequest data) {
        Order order = getOrderService.execute(data.getOrderId());
        Costumer costumer = getCostumerService.execute(data.getCostumerId());

        Context context = new Context();

        context.setVariable("order", order);
        context.setVariable("costumer", costumer);
        context.setVariable("clauses", data.getClauses());
        context.setVariable("code", generateContractCode());

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

    private String generateContractCode() {
        int code = (int) (Math.random() * 10000);
        return String.format("%04d", code);
    }

}
