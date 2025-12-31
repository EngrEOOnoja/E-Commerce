// ProductJpaRepository.java
package com.ecommerce.backend.infrastructure.persistence.product.repository;

import com.ecommerce.backend.infrastructure.persistence.product.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {
    List<ProductJpaEntity> findByCategory(String category);
    List<ProductJpaEntity> findByNameContainingIgnoreCase(String name);
}
