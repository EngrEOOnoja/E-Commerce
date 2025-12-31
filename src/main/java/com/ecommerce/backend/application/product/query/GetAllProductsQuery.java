// GetAllProductsQuery.java
package com.ecommerce.backend.application.product.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAllProductsQuery {
    private final String category; // Optional filter

    public GetAllProductsQuery() {
        this.category = null;
    }
}