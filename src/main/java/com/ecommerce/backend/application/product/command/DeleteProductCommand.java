package com.ecommerce.backend.application.product.command;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;

@Getter
@NoArgsConstructor(force = true)  // Creates no-arg constructor with default values
@AllArgsConstructor
public class DeleteProductCommand {
    private final Long productId;
}