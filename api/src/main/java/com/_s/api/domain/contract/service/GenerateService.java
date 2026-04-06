package com._s.api.domain.contract.service;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com._s.api.domain.contract.Contract;
import com._s.api.domain.contract.ContractReferenceType;
import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.order.Order;
import com._s.api.domain.rent.Rent;
import com._s.api.domain.user.User;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenerateService {
    private static final String DEFAULT_LOGO_URL =
            "https://res.cloudinary.com/duxksghsb/image/upload/v1771003225/WhatsApp_Image_2026-02-13_at_14.19.16_q3aisl.jpg";

    private final SpringTemplateEngine engine;

    public byte[] generatePdf(
        Contract contract,
        Object reference,
        Costumer costumer,
        User user
    ) {
        Context context = new Context();
        ReferenceTemplateData templateData = buildReferenceTemplateData(contract.getReferenceType(), reference);

        context.setVariable("reference", reference);
        context.setVariable("referenceType", contract.getReferenceType());
        context.setVariable("createdAt", templateData.createdAt());
        context.setVariable("deliveryDate", templateData.deliveryDate());
        context.setVariable("eventLocation", templateData.eventLocation());
        context.setVariable("items", templateData.items());
        context.setVariable("total", templateData.total());
        context.setVariable("costumer", costumer);
        context.setVariable("clauses", contract.getClauses());
        context.setVariable("code", contract.getCode());
        context.setVariable("user", user);
        context.setVariable("discount", "0,00");
        context.setVariable("finalTotal", templateData.total());
        context.setVariable("logoUrl", resolveLogoUrl(user));

        String html = engine.process("contract", context);

        return generatePdfFromHtml(html);
    }

    static byte[] generatePdfFromHtml(String html) {
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

    private ReferenceTemplateData buildReferenceTemplateData(ContractReferenceType referenceType, Object reference) {
        if (referenceType == ContractReferenceType.ORDER && reference instanceof Order order) {
            return new ReferenceTemplateData(
                    order.getCreatedAt(),
                    order.getDeliveryDate(),
                    formatEventLocation(order.getDeliveryAddress()),
                    order.getItems(),
                    order.getTotal()
            );
        }

        if (referenceType == ContractReferenceType.RENT && reference instanceof Rent rent) {
            return new ReferenceTemplateData(
                    rent.getCreatedAt(),
                    rent.getDeliveryDate(),
                    formatEventLocation(rent.getDeliveryAddress()),
                    rent.getItems(),
                    rent.getTotal()
            );
        }

        throw new IllegalArgumentException("Referência de contrato inválida.");
    }

    private String formatEventLocation(com._s.api.domain.shared.Address address) {
        if (address == null) {
            return "";
        }

        return "%s, %s - %s".formatted(address.getStreet(), address.getNumber(), address.getCity());
    }

    private String resolveLogoUrl(User user) {
        if (user.getLogo() != null && !user.getLogo().isBlank()) {
            return user.getLogo();
        }

        return DEFAULT_LOGO_URL;
    }

    private record ReferenceTemplateData(
            java.time.LocalDateTime createdAt,
            java.time.LocalDateTime deliveryDate,
            String eventLocation,
            Object items,
            java.math.BigDecimal total
    ) {
    }
}
