package com._s.api.domain.rent.service;

import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.costumer.CostumerNotFoundException;
import com._s.api.domain.costumer.CostumerRepository;
import com._s.api.domain.product.Product;
import com._s.api.domain.product.ProductRepository;
import com._s.api.domain.product.exception.ProductNotFoundException;
import com._s.api.domain.rent.Rent;
import com._s.api.domain.rent.RentRepository;
import com._s.api.domain.rent.RentStatus;
import com._s.api.domain.rent.exception.ProductUnavailableForPeriodException;
import com._s.api.domain.rentItem.RentItem;
import com._s.api.domain.rentItem.RentItemPolicy;
import com._s.api.domain.user.User;
import com._s.api.domain.user.UserRepository;
import com._s.api.domain.user.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com._s.api.presentation.dto.CreateRentItemRequest;
import com._s.api.presentation.dto.CreateRentRequest;
import com._s.api.presentation.mapper.shared.AddressRequestMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CreateRentService {

    private final RentRepository repository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CostumerRepository costumerRepository;
    private final RentItemPolicy rentItemPolicy;

    public CreateRentService(
            RentRepository repository,
            ProductRepository productRepository,
            UserRepository userRepository,
            CostumerRepository costumerRepository,
            RentItemPolicy rentItemPolicy
    ) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.costumerRepository = costumerRepository;
        this.rentItemPolicy = rentItemPolicy;
    }

    @Transactional
    public Rent execute(CreateRentCommand command, String userId) {
        CreateRentRequest request = command.getRequest();

        Optional<User> user = userRepository.findById(userId);
        Optional<Costumer> costumer = costumerRepository.findById(request.getCostumerId());

        if (user.isEmpty()) {
            throw new UserNotFoundException("Usuário não encontrado");
        }

        if (costumer.isEmpty()) {
            throw new CostumerNotFoundException("Cliente não encontrado");
        }

        List<String> productIds = request.getItems()
                .stream()
                .map(CreateRentItemRequest::getProductId)
                .toList();

        Map<String, Product> productsById = productRepository.findAllByIdIn(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        validateProductStockByPeriod(request, productsById);

        List<RentItem> items = new ArrayList<>();

        request.getItems().forEach(item -> {
            Product product = productsById.get(item.getProductId());

            if (product == null) {
                throw new ProductNotFoundException("Produto: " + item.getProductId() + " não encontrado");
            }

            rentItemPolicy.validateProductQuantity(item.getQuantity(), product);

            RentItem rentItem = new RentItem(product, product.getPrice(), item.getQuantity());
            rentItem.calculateSubTotal();
            items.add(rentItem);
        });

        Rent rent = new Rent(
                user.get().getId(),
                costumer.get().getId(),
                RentStatus.REALIZADO,
                items,
                request.getDeliveryTax(),
                AddressRequestMapper.toDomain(request.getDeliveryAddress()),
                request.getDeliveryDate(),
                request.getReturnDate()
        );

        rent.calculateTotal();

        return repository.save(rent);
    }

    private void validateProductStockByPeriod(
            CreateRentRequest request,
            Map<String, Product> productsById
    ) {
        List<RentStatus> activeStatuses = List.of(
                RentStatus.PENDENTE,
                RentStatus.REALIZADO,
                RentStatus.EM_ANDAMENTO
        );

        request.getItems().forEach(item -> {
            Product product = productsById.get(item.getProductId());

            if (product == null) {
                throw new ProductNotFoundException("Produto: " + item.getProductId() + " não encontrado");
            }

            Integer reservedQuantity = repository.sumReservedQuantityByProductAndPeriod(
                    product.getId(),
                    request.getDeliveryDate(),
                    request.getReturnDate(),
                    activeStatuses
            );

            int availableStock = product.getStock() - (reservedQuantity == null ? 0 : reservedQuantity);

            if (item.getQuantity() > availableStock) {
                throw new ProductUnavailableForPeriodException(
                        "Produto " + product.getName() + " indisponível para o período selecionado."
                );
            }
        });
    }
}
