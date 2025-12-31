// ProductPersistenceMapper.java
package com.ecommerce.backend.infrastructure.persistence.product.mapper;

import com.ecommerce.backend.domain.product.entity.Product;
import com.ecommerce.backend.domain.product.valueobject.*;
import com.ecommerce.backend.domain.shared.valueobject.Money;
import com.ecommerce.backend.infrastructure.persistence.product.entity.ProductJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductPersistenceMapper {

    public ProductJpaEntity toJpaEntity(Product product) {
        return ProductJpaEntity.builder()
                .id(product.getId() != null ? product.getId().getValue() : null)
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice().getAmount())
                .stockQuantity(product.getStockQuantity().getValue())
                .imageUrl(product.getImageUrl())
                .category(product.getCategory())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public Product toDomainEntity(ProductJpaEntity jpaEntity) {
        return Product.reconstitute(
                ProductId.of(jpaEntity.getId()),
                jpaEntity.getName(),
                jpaEntity.getDescription(),
                Money.of(jpaEntity.getPrice()),
                StockQuantity.of(jpaEntity.getStockQuantity()),
                jpaEntity.getImageUrl(),
                jpaEntity.getCategory(),
                jpaEntity.getCreatedAt(),
                jpaEntity.getUpdatedAt()
        );
    }
}
