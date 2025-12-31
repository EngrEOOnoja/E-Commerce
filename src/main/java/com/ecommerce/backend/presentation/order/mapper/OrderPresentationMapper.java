package com.ecommerce.backend.presentation.order.mapper;

import com.ecommerce.backend.domain.order.entity.Order;
import com.ecommerce.backend.domain.order.entity.OrderItem;
import com.ecommerce.backend.presentation.order.dto.OrderItemResponse;
import com.ecommerce.backend.presentation.order.dto.OrderResponse;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class OrderPresentationMapper {

    public OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId().getValue())
                .customerEmail(order.getCustomerEmail().getValue())     // CHANGED
                .shippingAddress(order.getShippingAddress().getValue()) // ADDED
                .items(order.getItems().stream()
                        .map(this::toItemResponse)
                        .collect(Collectors.toList()))
                .totalAmount(order.getTotalAmount().getAmount())
                .status(order.getStatus().name())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    private OrderItemResponse toItemResponse(OrderItem item) {
        return OrderItemResponse.builder()
                .id(item.getId())
                .productId(item.getProductId().getValue())
                .productName(item.getProductName())
                .unitPrice(item.getUnitPrice().getAmount())
                .quantity(item.getQuantity())
                .subtotal(item.getSubtotal().getAmount())
                .build();
    }
}