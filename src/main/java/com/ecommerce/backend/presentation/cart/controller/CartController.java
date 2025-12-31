// CartController.java (Complete)
package com.ecommerce.backend.presentation.cart.controller;

import com.ecommerce.backend.application.cart.command.*;
import com.ecommerce.backend.application.cart.query.GetCartQuery;
import com.ecommerce.backend.application.cart.service.CartApplicationService;
import com.ecommerce.backend.domain.cart.entity.Cart;
import com.ecommerce.backend.presentation.cart.dto.*;
import com.ecommerce.backend.presentation.cart.mapper.CartPresentationMapper;
import com.ecommerce.backend.presentation.shared.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CartController {
    private final CartApplicationService cartService;
    private final CartPresentationMapper mapper;

    @PostMapping
    public ResponseEntity<ApiResponse<CartResponse>> createCart() {
        Cart cart = cartService.createCart();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Cart created successfully", mapper.toResponse(cart)));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<ApiResponse<CartResponse>> getCart(@PathVariable Long cartId) {
        Cart cart = cartService.getCart(new GetCartQuery(cartId));
        return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(cart)));
    }

    @PostMapping("/{cartId}/add")
    public ResponseEntity<ApiResponse<CartResponse>> addToCart(
            @PathVariable Long cartId,
            @Valid @RequestBody AddToCartRequest request) {

        AddToCartCommand command = new AddToCartCommand(
                cartId,
                request.getProductId(),
                request.getQuantity()
        );

        Cart cart = cartService.addToCart(command);
        return ResponseEntity.ok(ApiResponse.success("Item added to cart", mapper.toResponse(cart)));
    }

    @DeleteMapping("/{cartId}/items/{cartItemId}")
    public ResponseEntity<ApiResponse<CartResponse>> removeFromCart(
            @PathVariable Long cartId,
            @PathVariable Long cartItemId) {

        RemoveFromCartCommand command = new RemoveFromCartCommand(cartId, cartItemId);
        Cart cart = cartService.removeFromCart(command);
        return ResponseEntity.ok(ApiResponse.success("Item removed from cart", mapper.toResponse(cart)));
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse<Void>> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(new ClearCartCommand(cartId));
        return ResponseEntity.ok(ApiResponse.success("Cart cleared successfully", null));
    }
}
