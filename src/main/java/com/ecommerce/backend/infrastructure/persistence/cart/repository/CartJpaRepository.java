// CartJpaRepository.java
package com.ecommerce.backend.infrastructure.persistence.cart.repository;

import com.ecommerce.backend.infrastructure.persistence.cart.entity.CartJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartJpaRepository extends JpaRepository<CartJpaEntity, Long> {
}
