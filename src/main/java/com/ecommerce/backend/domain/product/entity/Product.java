// Product.java (Aggregate Root)
package com.ecommerce.backend.domain.product.entity;

import com.ecommerce.backend.domain.product.valueobject.*;
import com.ecommerce.backend.domain.shared.valueobject.Money;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Product {
    private ProductId id;
    private String name;
    private String description;
    private Money price;
    private StockQuantity stockQuantity;
    private String imageUrl;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Private constructor for reconstruction
    private Product() {}

    // Factory method for creating new product
    public static Product create(
            String name,
            String description,
            Money price,
            StockQuantity stockQuantity,
            String imageUrl,
            String category) {

        Product product = new Product();
        product.name = name;
        product.description = description;
        product.price = price;
        product.stockQuantity = stockQuantity;
        product.imageUrl = imageUrl;
        product.category = category;
        product.createdAt = LocalDateTime.now();
        product.updatedAt = LocalDateTime.now();

        return product;
    }

    // Reconstitute from persistence
    public static Product reconstitute(
            ProductId id,
            String name,
            String description,
            Money price,
            StockQuantity stockQuantity,
            String imageUrl,
            String category,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        Product product = new Product();
        product.id = id;
        product.name = name;
        product.description = description;
        product.price = price;
        product.stockQuantity = stockQuantity;
        product.imageUrl = imageUrl;
        product.category = category;
        product.createdAt = createdAt;
        product.updatedAt = updatedAt;

        return product;
    }

    // Business methods
    public void updateDetails(String name, String description, Money price, String imageUrl, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }

    public void adjustStock(int quantity) {
        if (quantity < 0 && !stockQuantity.isSufficient(Math.abs(quantity))) {
            throw new IllegalStateException("Insufficient stock");
        }
        this.stockQuantity = quantity >= 0
                ? stockQuantity.increase(quantity)
                : stockQuantity.decrease(Math.abs(quantity));
        this.updatedAt = LocalDateTime.now();
    }

    public boolean hasStock(int required) {
        return stockQuantity.isSufficient(required);
    }
}