package com._s.api.domain.receipt.service;

import org.springframework.stereotype.Service;

import com._s.api.domain.contract.ContractReferenceType;
import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.costumer.service.GetCostumerService;
import com._s.api.domain.order.Order;
import com._s.api.domain.order.service.GetOrderService;
import com._s.api.domain.rent.Rent;
import com._s.api.domain.rent.service.GetRentService;
import com._s.api.domain.user.User;
import com._s.api.domain.user.service.GetUserService;
import com._s.api.presentation.dto.CreatedReceipt;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateReceiptService {
    private final GetOrderService getOrderService;
    private final GetRentService getRentService;
    private final GetCostumerService getCostumerService;
    private final GetUserService getUserService;
    private final GenerateReceiptService generateReceiptService;

    public CreatedReceipt execute(CreateReceiptCommand command, String userId) {
        User user = getUserService.executeById(userId);
        ReferenceData referenceData = resolveReference(command.getReferenceId(), command.getReferenceType());
        Costumer costumer = getCostumerService.execute(referenceData.costumerId());

        byte[] pdf = generateReceiptService.generatePdf(
                command.getReferenceType(),
                referenceData.reference(),
                costumer,
                user
        );

        return new CreatedReceipt(pdf, referenceData.referenceCode());
    }

    private ReferenceData resolveReference(String referenceId, ContractReferenceType referenceType) {
        if (referenceType == ContractReferenceType.ORDER) {
            Order order = getOrderService.execute(referenceId);
            return new ReferenceData(order, order.getCostumerId(), order.getCode());
        }

        Rent rent = getRentService.execute(referenceId);
        return new ReferenceData(rent, rent.getCostumerId(), rent.getCode());
    }

    private record ReferenceData(Object reference, String costumerId, String referenceCode) {
    }
}
