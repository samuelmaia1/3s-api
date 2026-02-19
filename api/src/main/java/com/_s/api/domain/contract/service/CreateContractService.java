package com._s.api.domain.contract.service;

import com._s.api.domain.clause.Clause;
import com._s.api.domain.clause.ClauseRepository;
import com._s.api.domain.contract.Contract;
import com._s.api.domain.contract.ContractRepository;
import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.costumer.service.GetCostumerService;
import com._s.api.domain.order.Order;
import com._s.api.domain.order.service.GetOrderService;
import com._s.api.domain.user.User;
import com._s.api.domain.user.UserRepository;
import com._s.api.domain.user.service.GetUserService;
import com._s.api.infra.mappers.OrderMapper;
import com._s.api.presentation.dto.ContractRequest;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateContractService {

    private final SpringTemplateEngine engine;
    private final GetOrderService getOrderService;
    private final GetCostumerService getCostumerService;
    private final ContractRepository contractRepository;
    private final ClauseRepository clauseRepository;
    private final GetUserService getUserService;

    public byte[] execute(CreateContractCommand data, String userId) {
        Order order = getOrderService.execute(data.getOrderId());
        Costumer costumer = getCostumerService.execute(data.getCostumerId());
        User user = getUserService.executeById(userId);

        List<Clause> clauses = data
            .getClausesIds()
            .stream()
            .map(id -> clauseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cláusula não encontrada")))
            .toList();
        ;

        Contract contract = new Contract(
            costumer.getId(),
            order.getId(),
            clauses
        );

        Contract saveContract = contractRepository.save(contract);

        return generatePdf(saveContract, order, costumer, user);
    }

    private byte[] generatePdf(
        Contract contract,
        Order order,
        Costumer costumer,
        User user
    ) {

        Context context = new Context();

        context.setVariable("order", order);
        context.setVariable("costumer", costumer);
        context.setVariable("clauses", contract.getClauses());
        context.setVariable("code", contract.getCode());
        context.setVariable("user", user);
        context.setVariable("logoUrl", "https://res.cloudinary.com/duxksghsb/image/upload/v1771003225/WhatsApp_Image_2026-02-13_at_14.19.16_q3aisl.jpg");

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
