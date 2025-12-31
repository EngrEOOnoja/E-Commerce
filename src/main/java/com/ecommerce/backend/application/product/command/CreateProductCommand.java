package com.ecommerce.backend.application.product.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor(access = lombok.AccessLevel.PUBLIC)
public class CreateProductCommand {
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final int stockQuantity;
    private final String imageUrl;
    private final String category;
}