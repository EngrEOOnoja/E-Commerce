// CartItem.java (Entity within Cart Aggregate)
package com.ecommerce.backend.domain.cart.entity;

import com.ecommerce.backend.domain.product.valueobject.ProductId;
import com.ecommerce.backend.domain.shared.valueobject.Money;
import lombok.Getter;

@Getter
public class CartItem {
    private Long id;
    private ProductId productId;
    private String productName;
    private Money unitPrice;
    private int quantity;

    private CartItem() {}

    public static CartItem create(ProductId productId, String productName, Money unitPrice, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        CartItem item = new CartItem();
        item.productId = productId;
        item.productName = productName;
        item.unitPrice = unitPrice;
        item.quantity = quantity;
        return item;
    }

    public static CartItem reconstitute(Long id, ProductId productId, String productName, Money unitPrice, int quantity) {
        CartItem item = create(productId, productName, unitPrice, quantity);
        item.id = id;
        return item;
    }

    public void increaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.quantity += amount;
    }

    public Money calculateSubtotal() {
        return unitPrice.multiply(quantity);
    }

    // ADD THIS METHOD
    public Money getSubtotal() {
        return calculateSubtotal();
    }

    public boolean isForProduct(ProductId productId) {
        return this.productId.equals(productId);
    }
}