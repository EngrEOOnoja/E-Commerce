package com.ecommerce.backend.presentation.cart.mapper;

import com.ecommerce.backend.domain.cart.entity.Cart;
import com.ecommerce.backend.domain.cart.entity.CartItem;
import com.ecommerce.backend.presentation.cart.dto.CartItemResponse;
import com.ecommerce.backend.presentation.cart.dto.CartResponse;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class CartPresentationMapper {

    public CartResponse toResponse(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId().getValue())
                .items(cart.getItems().stream()
                        .map(this::toItemResponse)
                        .collect(Collectors.toList()))
                .totalAmount(cart.getTotalAmount().getAmount())
                .createdAt(cart.getCreatedAt())
                .build();
    }

    private CartItemResponse toItemResponse(CartItem item) {
        return CartItemResponse.builder()
                .id(item.getId())
                .productId(item.getProductId().getValue())
                .productName(item.getProductName())
                .unitPrice(item.getUnitPrice().getAmount())
                .quantity(item.getQuantity())
                .subtotal(item.getSubtotal().getAmount())
                .build();
    }
}