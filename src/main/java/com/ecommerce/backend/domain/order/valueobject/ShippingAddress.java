// ShippingAddress.java (Value Object)
package com.ecommerce.backend.domain.order.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ShippingAddress {
    private final String value;

    private ShippingAddress(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Shipping address cannot be empty");
        }
        this.value = value;
    }

    public static ShippingAddress of(String value) {
        return new ShippingAddress(value);
    }
}
