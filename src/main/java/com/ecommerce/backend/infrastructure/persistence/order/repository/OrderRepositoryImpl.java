// OrderRepositoryImpl.java
package com.ecommerce.backend.infrastructure.persistence.order.repository;

import com.ecommerce.backend.domain.order.entity.Order;
import com.ecommerce.backend.domain.order.repository.OrderRepository;
import com.ecommerce.backend.domain.order.valueobject.OrderId;
import com.ecommerce.backend.domain.shared.valueobject.Email;
import com.ecommerce.backend.infrastructure.persistence.order.mapper.OrderPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository jpaRepository;
    private final OrderPersistenceMapper mapper;

    @Override
    public Order save(Order order) {
        var jpaEntity = mapper.toJpaEntity(order);
        var saved = jpaRepository.save(jpaEntity);
        return mapper.toDomainEntity(saved);
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        return jpaRepository.findById(id.getValue())
                .map(mapper::toDomainEntity);
    }

    @Override
    public List<Order> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByCustomerEmail(Email email) {
        return jpaRepository.findByCustomerEmail(email.getValue()).stream()
                .map(mapper::toDomainEntity)
                .collect(Collectors.toList());
    }
}
