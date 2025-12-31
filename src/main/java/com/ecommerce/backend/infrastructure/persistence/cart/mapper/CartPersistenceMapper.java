package com.ecommerce.backend.infrastructure.persistence.cart.mapper;

import com.ecommerce.backend.domain.cart.entity.Cart;
import com.ecommerce.backend.domain.cart.entity.CartItem;
import com.ecommerce.backend.domain.cart.valueobject.CartId;
import com.ecommerce.backend.domain.product.valueobject.ProductId;
import com.ecommerce.backend.domain.shared.valueobject.Money;
import com.ecommerce.backend.infrastructure.persistence.cart.entity.CartJpaEntity;
import com.ecommerce.backend.infrastructure.persistence.cart.entity.CartItemJpaEntity;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class CartPersistenceMapper {

    public CartJpaEntity toJpaEntity(Cart cart) {
        CartJpaEntity jpaEntity = CartJpaEntity.builder()
                .id(cart.getId() != null ? cart.getId().getValue() : null)
                .createdAt(cart.getCreatedAt())
                .build();

        cart.getItems().forEach(item -> {
            CartItemJpaEntity itemEntity = toJpaItemEntity(item);
            jpaEntity.addItem(itemEntity);
        });

        return jpaEntity;
    }

    public Cart toDomain(CartJpaEntity jpaEntity) {
        return Cart.reconstitute(
                CartId.of(jpaEntity.getId()),
                jpaEntity.getItems().stream()
                        .map(this::toDomainItemEntity)
                        .collect(Collectors.toList()),
                jpaEntity.getCreatedAt()
        );
    }

    private CartItemJpaEntity toJpaItemEntity(CartItem item) {
        return CartItemJpaEntity.builder()
                .id(item.getId())
                .productId(item.getProductId().getValue())
                .productName(item.getProductName())
                .unitPrice(item.getUnitPrice().getAmount())
                .quantity(item.getQuantity())
                .build();
    }

    private CartItem toDomainItemEntity(CartItemJpaEntity jpaEntity) {
        return CartItem.reconstitute(
                jpaEntity.getId(),
                ProductId.of(jpaEntity.getProductId()),
                jpaEntity.getProductName(),
                Money.of(jpaEntity.getUnitPrice()),
                jpaEntity.getQuantity()
        );
    }
}