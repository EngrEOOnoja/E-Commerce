// CartRepositoryImpl.java
package com.ecommerce.backend.infrastructure.persistence.cart.repository;

import com.ecommerce.backend.domain.cart.entity.Cart;
import com.ecommerce.backend.domain.cart.repository.CartRepository;
import com.ecommerce.backend.domain.cart.valueobject.CartId;
import com.ecommerce.backend.infrastructure.persistence.cart.mapper.CartPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {

    private final CartJpaRepository jpaRepository;
    private final CartPersistenceMapper mapper;

    @Override
    public Cart save(Cart cart) {
        var jpaEntity = mapper.toJpaEntity(cart);
        var saved = jpaRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Cart> findById(CartId id) {
        return jpaRepository.findById(id.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public void deleteById(CartId id) {
        jpaRepository.deleteById(id.getValue());
    }
}
