// OrderJpaRepository.java
package com.ecommerce.backend.infrastructure.persistence.order.repository;

import com.ecommerce.backend.infrastructure.persistence.order.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, Long> {
    List<OrderJpaEntity> findByCustomerEmail(String email);
}
