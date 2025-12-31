// ProductController.java (Complete)
package com.ecommerce.backend.presentation.product.controller;

import com.ecommerce.backend.application.product.command.*;
import com.ecommerce.backend.application.product.query.*;
import com.ecommerce.backend.application.product.service.ProductApplicationService;
import com.ecommerce.backend.domain.product.entity.Product;
import com.ecommerce.backend.presentation.product.dto.*;
import com.ecommerce.backend.presentation.product.mapper.ProductPresentationMapper;
import com.ecommerce.backend.presentation.shared.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductApplicationService productService;
    private final ProductPresentationMapper mapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts(
            @RequestParam(required = false) String category) {
        GetAllProductsQuery query = category != null
                ? new GetAllProductsQuery(category)
                : new GetAllProductsQuery();

        List<Product> products = productService.getAllProducts(query);
        List<ProductResponse> response = products.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable Long id) {
        Product product = productService.getProduct(new GetProductQuery(id));
        return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(product)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> searchProducts(
            @RequestParam String name) {
        List<Product> products = productService.searchProducts(new SearchProductsQuery(name));
        List<ProductResponse> response = products.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @Valid @RequestBody CreateProductRequest request) {

        CreateProductCommand command = new CreateProductCommand(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStockQuantity(),
                request.getImageUrl(),
                request.getCategory()
        );

        Product product = productService.createProduct(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Product created successfully", mapper.toResponse(product)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest request) {

        UpdateProductCommand command = UpdateProductCommand.builder()
                .productId(id)
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .category(request.getCategory())
                .stockQuantity(request.getStockQuantity())
                .build();

        Product product = productService.updateProduct(command);
        return ResponseEntity.ok(ApiResponse.success("Product updated successfully", mapper.toResponse(product)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(new DeleteProductCommand(id));
        return ResponseEntity.ok(ApiResponse.success("Product deleted successfully", null));
    }
}
