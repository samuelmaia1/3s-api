package com._s.api.domain.order.service;

import com._s.api.domain.costumer.Costumer;
import com._s.api.domain.costumer.CostumerNotFoundException;
import com._s.api.domain.costumer.CostumerRepository;
import com._s.api.domain.order.Order;
import com._s.api.domain.order.OrderRepository;
import com._s.api.domain.order.OrderStatus;
import com._s.api.domain.orderItem.OrderItem;
import com._s.api.domain.orderItem.OrderItemPolicy;
import com._s.api.domain.product.Product;
import com._s.api.domain.product.ProductRepository;
import com._s.api.domain.product.exception.ProductNotFoundException;
import com._s.api.domain.user.User;
import com._s.api.domain.user.UserRepository;
import com._s.api.domain.user.exception.UserNotFoundException;
import com._s.api.infra.mappers.CostumerMapper;
import com._s.api.infra.mappers.OrderMapper;
import com._s.api.infra.mappers.UserMapper;
import com._s.api.infra.repositories.entity.CostumerEntity;
import com._s.api.infra.repositories.entity.OrderEntity;
import com._s.api.infra.repositories.entity.UserEntity;
import com._s.api.presentation.dto.CreateOrderItemRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CreateOrderService {

    private final OrderRepository repository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CostumerRepository costumerRepository;
    private final OrderItemPolicy orderItemPolicy;

    public CreateOrderService(
            OrderRepository repository,
            ProductRepository productRepository,
            UserRepository userRepository,
            OrderItemPolicy orderItemPolicy,
            CostumerRepository costumerRepository
    ) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderItemPolicy = orderItemPolicy;
        this.costumerRepository = costumerRepository;
    }

    @Transactional
    public Order execute(CreateOrderCommand command, String userId) {
        Optional<User> user = userRepository.findById(userId);

        Optional<Costumer> costumer = costumerRepository.findById(command.getRequest().getCostumerId());

        if (user.isEmpty())
            throw new UserNotFoundException("Usuário não encontrado");

        if (costumer.isEmpty())
            throw new CostumerNotFoundException("Cliente não encontrado");

        List<String> productIds = command.getRequest()
                .getItems()
                .stream()
                .map(CreateOrderItemRequest::getProductId)
                .toList();

        Map<String, Product> productsById = productRepository
                .findAllByIdIn(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        List<OrderItem> items = new ArrayList<>();

        command.getRequest().getItems().forEach(item -> {
            Product product = productsById.get(item.getProductId());

            if (product == null) {
                throw new ProductNotFoundException("Produto: " + item.getProductId() + " não encontrado");
            }

            orderItemPolicy.validateProductQuantity(
                item.getQuantity(),
                product
            );

            productRepository.decreaseStock(product.getId(), item.getQuantity());

            product.setStock(product.getStock() - item.getQuantity());

            OrderItem orderItem = new OrderItem(product, product.getPrice(), item.getQuantity());

            orderItem.calculateSubTotal();

            items.add(orderItem);
        });

        UserEntity userEntity = UserMapper.toEntity(user.get());
        CostumerEntity costumerEntity = CostumerMapper.toEntity(costumer.get(), userEntity);

        Order order = new Order(userEntity.getId(), costumerEntity.getId(), OrderStatus.REALIZADO, items);

        order.calculateTotal();

        OrderEntity entity = OrderMapper.toEntity(order, userEntity, costumerEntity);

        return repository.save(OrderMapper.toDomain(entity));
    }
}
