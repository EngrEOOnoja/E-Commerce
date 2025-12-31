// GetOrdersByEmailQuery.java
package com.ecommerce.backend.application.order.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetOrdersByEmailQuery {
    private final String email;
}