// RemoveFromCartCommand.java (Fixed)
package com.ecommerce.backend.application.cart.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveFromCartCommand {
    private Long cartId;
    private Long cartItemId;
}
