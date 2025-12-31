// CartDomainService.java
package com.ecommerce.backend.domain.cart.service;

import com.ecommerce.backend.domain.cart.entity.Cart;
import com.ecommerce.backend.domain.product.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class CartDomainService {

    public void validateCartNotEmpty(Cart cart) {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }
    }

    public void validateStockForCart(Cart cart, Product product, int requestedQuantity) {
        if (!product.hasStock(requestedQuantity)) {
            throw new IllegalStateException(
                    String.format("Insufficient stock for product %s. Available: %d, Requested: %d",
                            product.getName(),
                            product.getStockQuantity().getValue(),
                            requestedQuantity)
            );
        }
    }
}
