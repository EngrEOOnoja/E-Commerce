// OrderRepository.java (Domain Repository Interface)
package com.ecommerce.backend.domain.order.repository;

import com.ecommerce.backend.domain.order.entity.Order;
import com.ecommerce.backend.domain.order.valueobject.OrderId;
import com.ecommerce.backend.domain.shared.valueobject.Email;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(OrderId id);
    List<Order> findAll();
    List<Order> findByCustomerEmail(Email email);
}
