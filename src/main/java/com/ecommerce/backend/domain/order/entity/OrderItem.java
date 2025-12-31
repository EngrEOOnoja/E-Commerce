package com.ecommerce.backend.domain.order.entity;

import com.ecommerce.backend.domain.product.valueobject.ProductId;
import com.ecommerce.backend.domain.shared.valueobject.Money;
import lombok.Getter;

@Getter
public class OrderItem {
    private final Long id;
    private final ProductId productId;
    private final String productName;
    private final Money unitPrice;
    private final int quantity;

    private OrderItem(Long id, ProductId productId, String productName,
                      Money unitPrice, int quantity) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    // Factory method for creating new items
    public static OrderItem create(ProductId productId, String productName,
                                   Money unitPrice, int quantity) {
        return new OrderItem(null, productId, productName, unitPrice, quantity);
    }

    // Factory method for reconstituting from persistence
    public static OrderItem reconstitute(Long id, ProductId productId, String productName,
                                         Money unitPrice, int quantity) {
        return new OrderItem(id, productId, productName, unitPrice, quantity);
    }

    // Add this method
    public Money getSubtotal() {
        return unitPrice.multiply(quantity);
    }
}