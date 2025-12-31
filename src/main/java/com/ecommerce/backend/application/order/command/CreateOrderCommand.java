// CreateOrderCommand.java (Fixed)
package com.ecommerce.backend.application.order.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderCommand {
    private Long cartId;
    private String customerEmail;
    private String shippingAddress;
}