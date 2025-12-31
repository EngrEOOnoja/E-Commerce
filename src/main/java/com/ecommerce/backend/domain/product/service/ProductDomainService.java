// ProductDomainService.java
package com.ecommerce.backend.domain.product.service;

import com.ecommerce.backend.domain.product.entity.Product;
import com.ecommerce.backend.domain.product.valueobject.ProductId;
import org.springframework.stereotype.Service;

@Service
public class ProductDomainService {

    public void validateProductUpdate(Product product, ProductId requestedId) {
        if (!product.getId().equals(requestedId)) {
            throw new IllegalArgumentException("Product ID mismatch");
        }
    }

    public void validateStockAvailability(Product product, int requestedQuantity) {
        if (!product.hasStock(requestedQuantity)) {
            throw new IllegalStateException(
                    String.format("Insufficient stock. Available: %d, Requested: %d",
                            product.getStockQuantity().getValue(), requestedQuantity)
            );
        }
    }
}
