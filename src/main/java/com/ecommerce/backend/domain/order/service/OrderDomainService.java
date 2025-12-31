// OrderDomainService.java
package com.ecommerce.backend.domain.order.service;

import com.ecommerce.backend.domain.cart.entity.Cart;
import com.ecommerce.backend.domain.order.entity.Order;
import com.ecommerce.backend.domain.order.entity.OrderItem;
import com.ecommerce.backend.domain.product.entity.Product;
import com.ecommerce.backend.domain.product.valueobject.ProductId;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDomainService {

    public void validateStock(List<OrderItem> items, List<Product> products) {
        for (OrderItem item : items) {
            Product product = findProduct(products, item.getProductId());
            if (!product.hasStock(item.getQuantity())) {
                throw new IllegalStateException(
                        "Insufficient stock for product: " + product.getName()
                );
            }
        }
    }

    public void reserveStock(List<OrderItem> items, List<Product> products) {
        for (OrderItem item : items) {
            Product product = findProduct(products, item.getProductId());
            product.adjustStock(-item.getQuantity());
        }
    }

    public List<OrderItem> convertCartToOrderItems(Cart cart) {
        return cart.getItems().stream()
                .map(cartItem -> OrderItem.create(
                        cartItem.getProductId(),
                        cartItem.getProductName(),
                        cartItem.getUnitPrice(),      // Money (3rd parameter)
                        cartItem.getQuantity()        //  int (4th parameter)
                ))
                .collect(Collectors.toList());
    }

    private Product findProduct(List<Product> products, ProductId productId) {
        return products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }
}
