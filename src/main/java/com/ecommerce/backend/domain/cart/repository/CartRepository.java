// CartRepository.java (Domain Repository Interface)
package com.ecommerce.backend.domain.cart.repository;

import com.ecommerce.backend.domain.cart.entity.Cart;
import com.ecommerce.backend.domain.cart.valueobject.CartId;
import java.util.Optional;

public interface CartRepository {
    Cart save(Cart cart);
    Optional<Cart> findById(CartId id);
    void deleteById(CartId id);
}
