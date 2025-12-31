// ClearCartCommand.java
package com.ecommerce.backend.application.cart.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClearCartCommand {
    private  Long cartId;
}
