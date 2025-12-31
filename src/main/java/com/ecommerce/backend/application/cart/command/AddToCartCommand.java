// AddToCartCommand.java
package com.ecommerce.backend.application.cart.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AddToCartCommand {
    private final Long cartId;
    private final Long productId;
    private final int quantity;
}
