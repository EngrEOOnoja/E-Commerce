// OrderPersistenceMapper.java
package com.ecommerce.backend.infrastructure.persistence.order.mapper;

import com.ecommerce.backend.domain.order.entity.*;
import com.ecommerce.backend.domain.order.valueobject.*;
import com.ecommerce.backend.domain.product.valueobject.ProductId;
import com.ecommerce.backend.domain.shared.valueobject.*;
import com.ecommerce.backend.infrastructure.persistence.order.entity.*;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class OrderPersistenceMapper {

    public OrderJpaEntity toJpaEntity(Order order) {
        OrderJpaEntity jpaEntity = OrderJpaEntity.builder()
                .id(order.getId() != null ? order.getId().getValue() : null)
                .totalAmount(order.getTotalAmount().getAmount())
                .status(OrderStatusEnum.valueOf(order.getStatus().name()))
                .customerEmail(order.getCustomerEmail().getValue())
                .shippingAddress(order.getShippingAddress().getValue())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())  // Add this line
                .build();

        order.getItems().forEach(item -> {
            OrderItemJpaEntity itemEntity = toJpaItemEntity(item);
            jpaEntity.addItem(itemEntity);
        });

        return jpaEntity;
    }

    public Order toDomainEntity(OrderJpaEntity jpaEntity) {
        return Order.reconstitute(
                OrderId.of(jpaEntity.getId()),
                Email.of(jpaEntity.getCustomerEmail()),                    // 2nd parameter
                ShippingAddress.of(jpaEntity.getShippingAddress()),        // 3rd parameter
                jpaEntity.getItems().stream()
                        .map(this::toDomainItemEntity)
                        .collect(Collectors.toList()),                     // 4th parameter
                Money.of(jpaEntity.getTotalAmount()),                      // 5th parameter
                OrderStatus.valueOf(jpaEntity.getStatus().name()),         // 6th parameter
                jpaEntity.getCreatedAt(),                                  // 7th parameter
                jpaEntity.getUpdatedAt()                                   // 8th parameter (ADD THIS)
        );
    }

    private OrderItemJpaEntity toJpaItemEntity(OrderItem item) {
        return OrderItemJpaEntity.builder()
                .id(item.getId())
                .productId(item.getProductId().getValue())
                .productName(item.getProductName())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice().getAmount())
                .build();
    }

    private OrderItem toDomainItemEntity(OrderItemJpaEntity jpaEntity) {
        return OrderItem.reconstitute(
                jpaEntity.getId(),
                ProductId.of(jpaEntity.getProductId()),
                jpaEntity.getProductName(),
                Money.of(jpaEntity.getUnitPrice()),        // Money comes BEFORE quantity
                jpaEntity.getQuantity()                     // quantity comes LAST
        );
    }
}