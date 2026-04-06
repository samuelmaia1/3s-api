package com._s.api.domain.receipt.service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com._s.api.domain.contract.ContractReferenceType;
import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.order.Order;
import com._s.api.domain.rent.Rent;
import com._s.api.domain.shared.Address;
import com._s.api.domain.user.User;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenerateReceiptService {
    private static final String DEFAULT_LOGO_URL =
            "https://res.cloudinary.com/duxksghsb/image/upload/v1771003225/WhatsApp_Image_2026-02-13_at_14.19.16_q3aisl.jpg";

    private final SpringTemplateEngine engine;

    public byte[] generatePdf(
            ContractReferenceType referenceType,
            Object reference,
            Costumer costumer,
            User user
    ) {
        ReceiptTemplateData templateData = buildTemplateData(referenceType, reference);

        Context context = new Context();
        context.setVariable("referenceType", referenceType);
        context.setVariable("referenceLabel", templateData.referenceLabel());
        context.setVariable("referenceCode", templateData.referenceCode());
        context.setVariable("issuedAt", templateData.issuedAt());
        context.setVariable("deliveryDate", templateData.deliveryDate());
        context.setVariable("returnDate", templateData.returnDate());
        context.setVariable("eventLocation", templateData.eventLocation());
        context.setVariable("items", templateData.items());
        context.setVariable("total", templateData.total());
        context.setVariable("deliveryTax", templateData.deliveryTax());
        context.setVariable("isRent", referenceType == ContractReferenceType.RENT);
        context.setVariable("costumer", costumer);
        context.setVariable("user", user);
        context.setVariable("logoUrl", resolveLogoUrl(user));

        String html = engine.process("receipt", context);
        return generatePdfFromHtml(html);
    }

    private ReceiptTemplateData buildTemplateData(ContractReferenceType referenceType, Object reference) {
        if (referenceType == ContractReferenceType.ORDER && reference instanceof Order order) {
            return new ReceiptTemplateData(
                    "Pedido",
                    order.getCode(),
                    order.getCreatedAt(),
                    order.getDeliveryDate(),
                    order.getReturnDate(),
                    formatAddress(order.getDeliveryAddress()),
                    order.getItems(),
                    order.getTotal(),
                    BigDecimal.ZERO
            );
        }

        if (referenceType == ContractReferenceType.RENT && reference instanceof Rent rent) {
            return new ReceiptTemplateData(
                    "Aluguel",
                    rent.getCode(),
                    rent.getCreatedAt(),
                    rent.getDeliveryDate(),
                    rent.getReturnDate(),
                    formatAddress(rent.getDeliveryAddress()),
                    rent.getItems(),
                    rent.getTotal(),
                    rent.getDeliveryTax() == null ? BigDecimal.ZERO : rent.getDeliveryTax()
            );
        }

        throw new IllegalArgumentException("Referência de recibo inválida.");
    }

    private String formatAddress(Address address) {
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

    private static byte[] generatePdfFromHtml(String html) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null);
            builder.toStream(outputStream);
            builder.run();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar recibo em PDF", e);
        }
    }

    private record ReceiptTemplateData(
            String referenceLabel,
            String referenceCode,
            LocalDateTime issuedAt,
            LocalDateTime deliveryDate,
            LocalDateTime returnDate,
            String eventLocation,
            Object items,
            BigDecimal total,
            BigDecimal deliveryTax
    ) {
    }
}
