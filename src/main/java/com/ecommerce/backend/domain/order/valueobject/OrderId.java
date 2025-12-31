// OrderId.java
package com.ecommerce.backend.domain.order.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class OrderId {
    private final Long value;

    private OrderId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Order ID must be positive");
        }
        this.value = value;
    }

    public static OrderId of(Long value) {
        return new OrderId(value);
    }
}
