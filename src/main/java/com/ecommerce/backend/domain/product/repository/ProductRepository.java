// ProductRepository.java (Domain Repository Interface)
package com.ecommerce.backend.domain.product.repository;

import com.ecommerce.backend.domain.product.entity.Product;
import com.ecommerce.backend.domain.product.valueobject.ProductId;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(ProductId id);
    List<Product> findAll();
    List<Product> findByCategory(String category);
    List<Product> findByNameContaining(String name);
    void deleteById(ProductId id);
    boolean existsById(ProductId id);
}
