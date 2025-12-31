// UpdateOrderStatusCommand.java (Fixed)
package com.ecommerce.backend.application.order.command;

import com.ecommerce.backend.domain.order.valueobject.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStatusCommand {
    private Long orderId;
    private OrderStatus status;
}