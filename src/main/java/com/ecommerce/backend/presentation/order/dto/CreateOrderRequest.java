// CreateOrderRequest.java
package com.ecommerce.backend.presentation.order.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest {
    @NotNull(message = "Cart ID is required")
    @Min(value = 1, message = "Cart ID must be positive")
    private Long cartId;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String customerEmail;

    @NotBlank(message = "Shipping address is required")
    @Size(min = 10, max = 500, message = "Address must be between 10 and 500 characters")
    private String shippingAddress;
}
