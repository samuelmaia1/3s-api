package com._s.api.domain.order.service;

import com._s.api.domain.contract.Contract;
import com._s.api.domain.contract.ContractRepository;
import com._s.api.domain.contract.service.UpdateContractService;
import com._s.api.domain.order.Order;
import com._s.api.domain.order.OrderRepository;
import com._s.api.domain.order.OrderStatus;
import com._s.api.domain.order.exception.OrderNotFoundException;
import com._s.api.domain.product.Product;
import com._s.api.domain.product.ProductRepository;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class UpdateOrderService {

    private final OrderRepository repository;
    private final UpdateContractService updateContractService;
    private final ContractRepository contractRepository;
    private final ProductRepository productRepository;

    public UpdateOrderService(
        OrderRepository repository, 
        UpdateContractService updateContractService,
        ContractRepository contractRepository,
        ProductRepository productRepository
    ) {
        this.repository = repository;
        this.updateContractService = updateContractService;
        this.contractRepository = contractRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Order updateStatus(String id, OrderStatus status, String userId) {
        if (status == OrderStatus.CANCELADO) {
            cancelOrder(id, userId);
        }

        repository.updateStatus(id, status);

        return repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado."));
    }

    public void cancelOrder(String id, String userId) {
        Optional<Order> optionalOrder = repository.findById(id);

        if (optionalOrder.isEmpty())
            throw new OrderNotFoundException("Pedido nao encontrado.");

        Optional<Contract> contract = contractRepository.findByOrderId(id);

        if (contract.isPresent())
            updateContractService.cancelContract(contract.get().getId());

        Order order = optionalOrder.get();

        List<Product> products = new ArrayList<Product>();
        
        order.getItems().forEach(item -> {
            item.getProduct().incrementStock(item.getQuantity());
            products.add(item.getProduct());
        });

        productRepository.saveAll(products, userId);
    }
}
