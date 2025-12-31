// OrderResponse.java
package com.ecommerce.backend.presentation.order.dto;

import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderResponse {
    private Long id;
    private List<OrderItemResponse> items;
    private BigDecimal totalAmount;
    private String status;
    private String customerEmail;
    private String shippingAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;  // ADD THIS LINE
}