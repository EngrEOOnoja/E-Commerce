// ProductId.java (Value Object)
package com.ecommerce.backend.domain.product.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ProductId {
    private final Long value;

    private ProductId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("Product ID must be positive");
        }
        this.value = value;
    }

    public static ProductId of(Long value) {
        return new ProductId(value);
    }
}
