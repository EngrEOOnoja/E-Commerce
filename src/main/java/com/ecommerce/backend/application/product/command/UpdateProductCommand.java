package com.ecommerce.backend.application.product.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor  // Add this annotation
public class UpdateProductCommand {
    private final Long productId;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final String imageUrl;
    private final String category;
    private final Integer stockQuantity;
}


