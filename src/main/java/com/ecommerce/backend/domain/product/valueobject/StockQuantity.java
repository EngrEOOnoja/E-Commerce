// StockQuantity.java (Value Object)
package com.ecommerce.backend.domain.product.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class StockQuantity {
    private final int value;

    private StockQuantity(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        this.value = value;
    }

    public static StockQuantity of(int value) {
        return new StockQuantity(value);
    }

    public boolean isSufficient(int required) {
        return this.value >= required;
    }

    public StockQuantity decrease(int amount) {
        return new StockQuantity(this.value - amount);
    }

    public StockQuantity increase(int amount) {
        return new StockQuantity(this.value + amount);
    }
}
