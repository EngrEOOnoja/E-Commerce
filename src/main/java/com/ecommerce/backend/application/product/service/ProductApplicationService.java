// ProductApplicationService.java (Complete)
package com.ecommerce.backend.application.product.service;

import com.ecommerce.backend.application.product.command.*;
import com.ecommerce.backend.application.product.query.*;
import com.ecommerce.backend.domain.product.entity.Product;
import com.ecommerce.backend.domain.product.repository.ProductRepository;
import com.ecommerce.backend.domain.product.service.ProductDomainService;
import com.ecommerce.backend.domain.product.valueobject.*;
import com.ecommerce.backend.domain.shared.valueobject.Money;
import com.ecommerce.backend.domain.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductApplicationService {
    private final ProductRepository productRepository;
    private final ProductDomainService productDomainService;

    @Transactional
    public Product createProduct(CreateProductCommand command) {
        log.info("Creating product: {}", command.getName());

        Product product = Product.create(
                command.getName(),
                command.getDescription(),
                Money.of(command.getPrice()),
                StockQuantity.of(command.getStockQuantity()),
                command.getImageUrl(),
                command.getCategory()
        );

        Product saved = productRepository.save(product);
        log.info("Product created with ID: {}", saved.getId().getValue());
        return saved;
    }

    @Transactional
    public Product updateProduct(UpdateProductCommand command) {
        log.info("Updating product ID: {}", command.getProductId());

        ProductId productId = ProductId.of(command.getProductId());
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.updateDetails(
                command.getName(),
                command.getDescription(),
                Money.of(command.getPrice()),
                command.getImageUrl(),
                command.getCategory()
        );

        int stockDifference = command.getStockQuantity() - product.getStockQuantity().getValue();
        if (stockDifference != 0) {
            product.adjustStock(stockDifference);
        }
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(DeleteProductCommand command) {
        log.info("Deleting product ID: {}", command.getProductId());

        ProductId productId = ProductId.of(command.getProductId());
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found");
        }
        productRepository.deleteById(productId);
    }

    @Transactional(readOnly = true)
    public Product getProduct(GetProductQuery query) {
        return productRepository.findById(ProductId.of(query.getProductId()))
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts(GetAllProductsQuery query) {
        if (query.getCategory() != null && !query.getCategory().isEmpty()) {
            return productRepository.findByCategory(query.getCategory());
        }
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Product> searchProducts(SearchProductsQuery query) {
        return productRepository.findByNameContaining(query.getSearchTerm());
    }
}