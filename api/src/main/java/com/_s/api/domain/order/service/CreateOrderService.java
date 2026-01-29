package com._s.api.domain.order.service;

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
import com._s.api.infra.mappers.OrderMapper;
import com._s.api.infra.mappers.UserMapper;
import com._s.api.infra.repositories.entity.OrderEntity;
import com._s.api.infra.repositories.entity.UserEntity;
import com._s.api.presentation.dto.CreateOrderItemRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CreateOrderService {

    private final OrderRepository repository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderItemPolicy orderItemPolicy;

    public CreateOrderService(
            OrderRepository repository,
            ProductRepository productRepository,
            UserRepository userRepository,
            OrderItemPolicy orderItemPolicy
    ) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderItemPolicy = orderItemPolicy;
    }

    public Order execute(CreateOrderCommand command, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

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

            OrderItem orderItem = new OrderItem(product, product.getPrice(), item.getQuantity());

            orderItem.calculateSubTotal();

            items.add(orderItem);
        });

        UserEntity userEntity = UserMapper.toEntity(user);

        Order order = new Order(userEntity.getId(), OrderStatus.REALIZADO, items);

        order.calculateTotal();

        OrderEntity entity = OrderMapper.toEntity(order, userEntity);

        return repository.save(OrderMapper.toDomain(entity));
    }
}
