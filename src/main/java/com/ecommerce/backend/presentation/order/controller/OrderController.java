// OrderController.java (Complete)
package com.ecommerce.backend.presentation.order.controller;

import com.ecommerce.backend.application.order.command.*;
import com.ecommerce.backend.application.order.query.*;
import com.ecommerce.backend.application.order.service.OrderApplicationService;
import com.ecommerce.backend.domain.order.entity.Order;
import com.ecommerce.backend.domain.order.valueobject.OrderStatus;
import com.ecommerce.backend.presentation.order.dto.*;
import com.ecommerce.backend.presentation.order.mapper.OrderPresentationMapper;
import com.ecommerce.backend.presentation.shared.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {
    private final OrderApplicationService orderService;
    private final OrderPresentationMapper mapper;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        CreateOrderCommand command = new CreateOrderCommand(
                request.getCartId(),
                request.getCustomerEmail(),
                request.getShippingAddress()
        );

        Order order = orderService.createOrder(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Order placed successfully", mapper.toResponse(order)));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrder(new GetOrderQuery(orderId));
        return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(order)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders(new GetAllOrdersQuery());
        List<OrderResponse> response = orders.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/customer")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByEmail(
            @RequestParam String email) {
        List<Order> orders = orderService.getOrdersByEmail(new GetOrdersByEmailQuery(email));
        List<OrderResponse> response = orders.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateOrderStatusRequest request) {

        UpdateOrderStatusCommand command = new UpdateOrderStatusCommand(
                orderId,
                OrderStatus.valueOf(request.getStatus())
        );

        Order order = orderService.updateOrderStatus(command);
        return ResponseEntity.ok(ApiResponse.success("Order status updated", mapper.toResponse(order)));
    }
}
