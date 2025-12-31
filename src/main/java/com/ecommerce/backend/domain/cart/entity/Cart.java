// Cart.java (Aggregate Root)
package com.ecommerce.backend.domain.cart.entity;

import com.ecommerce.backend.domain.cart.valueobject.CartId;
import com.ecommerce.backend.domain.product.valueobject.ProductId;
import com.ecommerce.backend.domain.shared.valueobject.Money;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Cart {
    private CartId id;
    private List<CartItem> items = new ArrayList<>();
    private LocalDateTime createdAt;

    private Cart() {}

    public static Cart create() {
        Cart cart = new Cart();
        cart.items = new ArrayList<>();
        cart.createdAt = LocalDateTime.now();
        return cart;
    }

    public static Cart reconstitute(CartId id, List<CartItem> items, LocalDateTime createdAt) {
        Cart cart = new Cart();
        cart.id = id;
        cart.items = new ArrayList<>(items);
        cart.createdAt = createdAt;
        return cart;
    }

    // Business methods
    public void addItem(ProductId productId, String productName, Money unitPrice, int quantity) {
        CartItem existingItem = findItemByProductId(productId);

        if (existingItem != null) {
            existingItem.increaseQuantity(quantity);
        } else {
            CartItem newItem = CartItem.create(productId, productName, unitPrice, quantity);
            items.add(newItem);
        }
    }

    public void removeItem(Long cartItemId) {
        items.removeIf(item -> item.getId().equals(cartItemId));
    }

    public void clear() {
        items.clear();
    }

    public Money calculateTotal() {
        return items.stream()
                .map(CartItem::calculateSubtotal)
                .reduce(Money.zero(), Money::add);
    }

    // ADD THIS METHOD
    public Money getTotalAmount() {
        return calculateTotal();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    private CartItem findItemByProductId(ProductId productId) {
        return items.stream()
                .filter(item -> item.isForProduct(productId))
                .findFirst()
                .orElse(null);
    }
}