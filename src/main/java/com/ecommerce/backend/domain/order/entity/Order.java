package com.ecommerce.backend.domain.order.entity;

import com.ecommerce.backend.domain.order.valueobject.OrderId;
import com.ecommerce.backend.domain.order.valueobject.OrderStatus;
import com.ecommerce.backend.domain.order.valueobject.ShippingAddress;
import com.ecommerce.backend.domain.shared.valueobject.Email;
import com.ecommerce.backend.domain.shared.valueobject.Money;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Order {
    private final OrderId id;
    private final Email customerEmail;  // Added
    private final ShippingAddress shippingAddress;  // Added
    private final List<OrderItem> items;
    private final Money totalAmount;
    private OrderStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Order(OrderId id, Email customerEmail, ShippingAddress shippingAddress,
                  List<OrderItem> items, Money totalAmount,
                  OrderStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.shippingAddress = shippingAddress;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Factory method for creating new orders
    public static Order create(Email customerEmail, ShippingAddress shippingAddress,
                               List<OrderItem> items) {
        Money total = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(Money.ZERO, Money::add);

        LocalDateTime now = LocalDateTime.now();
        return new Order(null, customerEmail, shippingAddress, new ArrayList<>(items),
                total, OrderStatus.PENDING, now, now);
    }

    // Factory method for reconstituting from persistence
    public static Order reconstitute(OrderId id, Email customerEmail,
                                     ShippingAddress shippingAddress,
                                     List<OrderItem> items, Money totalAmount,
                                     OrderStatus status, LocalDateTime createdAt,
                                     LocalDateTime updatedAt) {
        return new Order(id, customerEmail, shippingAddress, items, totalAmount,
                status, createdAt, updatedAt);
    }

    public void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public void confirm() {
        this.status = OrderStatus.CONFIRMED;
        this.updatedAt = LocalDateTime.now();
    }

    public void ship() {
        this.status = OrderStatus.SHIPPED;
        this.updatedAt = LocalDateTime.now();
    }

    public void deliver() {
        this.status = OrderStatus.DELIVERED;
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        this.status = OrderStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }
}