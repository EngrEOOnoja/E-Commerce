package com.ecommerce.backend.presentation.product.mapper;

import com.ecommerce.backend.domain.product.entity.Product;
import com.ecommerce.backend.presentation.product.dto.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductPresentationMapper {

    public ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId().getValue())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice().getAmount())
                .imageUrl(product.getImageUrl())
                .category(product.getCategory())
                .stockQuantity(product.getStockQuantity().getValue())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}