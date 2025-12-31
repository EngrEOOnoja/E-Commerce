// SearchProductsQuery.java
package com.ecommerce.backend.application.product.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchProductsQuery {
    private final String searchTerm;
}