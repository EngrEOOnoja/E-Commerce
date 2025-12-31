// CartId.java (Value Object)
package com.ecommerce.backend.domain.cart.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class CartId {
    private final Long value;

    private CartId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Cart ID must be positive");
        }
        this.value = value;
    }

    public static CartId of(Long value) {
        return new CartId(value);
    }
}
