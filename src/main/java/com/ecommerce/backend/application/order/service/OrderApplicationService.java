// OrderApplicationService.java (Complete)
package com.ecommerce.backend.application.order.service;

import com.ecommerce.backend.application.order.command.*;
import com.ecommerce.backend.application.order.query.*;
import com.ecommerce.backend.domain.cart.entity.Cart;
import com.ecommerce.backend.domain.cart.repository.CartRepository;
import com.ecommerce.backend.domain.cart.valueobject.CartId;
import com.ecommerce.backend.domain.order.entity.Order;
import com.ecommerce.backend.domain.order.entity.OrderItem;
import com.ecommerce.backend.domain.order.repository.OrderRepository;
import com.ecommerce.backend.domain.order.service.OrderDomainService;
import com.ecommerce.backend.domain.order.valueobject.*;
import com.ecommerce.backend.domain.product.entity.Product;
import com.ecommerce.backend.domain.product.repository.ProductRepository;
import com.ecommerce.backend.domain.product.valueobject.ProductId;
import com.ecommerce.backend.domain.shared.valueobject.Email;
import com.ecommerce.backend.domain.shared.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderApplicationService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderDomainService orderDomainService;

    @Transactional
    public Order createOrder(CreateOrderCommand command) {
        log.info("Creating order for email: {}", command.getCustomerEmail());

        Cart cart = cartRepository.findById(CartId.of(command.getCartId()))
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        if (cart.isEmpty()) {
            throw new BusinessRuleViolationException("Cannot create order from empty cart");
        }

        List<OrderItem> orderItems = orderDomainService.convertCartToOrderItems(cart);

        List<ProductId> productIds = orderItems.stream()
                .map(OrderItem::getProductId)
                .toList();

        List<Product> products = productIds.stream()
                .map(id -> productRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found")))
                .collect(Collectors.toList());

        orderDomainService.validateStock(orderItems, products);

        Order order = Order.create(
                Email.of(command.getCustomerEmail()),
                ShippingAddress.of(command.getShippingAddress()),
                orderItems
        );

        orderDomainService.reserveStock(orderItems, products);
        products.forEach(productRepository::save);

        Order savedOrder = orderRepository.save(order);

        cart.clear();
        cartRepository.save(cart);

        log.info("Order created with ID: {}", savedOrder.getId().getValue());
        return savedOrder;
    }

    @Transactional(readOnly = true)
    public Order getOrder(GetOrderQuery query) {
        return orderRepository.findById(OrderId.of(query.getOrderId()))
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders(GetAllOrdersQuery query) {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByEmail(GetOrdersByEmailQuery query) {
        return orderRepository.findByCustomerEmail(Email.of(query.getEmail()));
    }

    @Transactional
    public Order updateOrderStatus(UpdateOrderStatusCommand command) {
        log.info("Updating order {} status to {}", command.getOrderId(), command.getStatus());

        Order order = getOrder(new GetOrderQuery(command.getOrderId()));
        order.updateStatus(command.getStatus());
        return orderRepository.save(order);
    }
}
