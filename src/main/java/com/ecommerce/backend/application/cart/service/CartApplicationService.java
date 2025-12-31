// CartApplicationService.java (Complete)
package com.ecommerce.backend.application.cart.service;

import com.ecommerce.backend.application.cart.command.*;
import com.ecommerce.backend.application.cart.query.*;
import com.ecommerce.backend.domain.cart.entity.Cart;
import com.ecommerce.backend.domain.cart.repository.CartRepository;
import com.ecommerce.backend.domain.cart.service.CartDomainService;
import com.ecommerce.backend.domain.cart.valueobject.CartId;
import com.ecommerce.backend.domain.product.entity.Product;
import com.ecommerce.backend.domain.product.repository.ProductRepository;
import com.ecommerce.backend.domain.product.valueobject.ProductId;
import com.ecommerce.backend.domain.shared.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartApplicationService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartDomainService cartDomainService;

    @Transactional
    public Cart createCart() {
        log.info("Creating new cart");
        Cart cart = Cart.create();
        Cart saved = cartRepository.save(cart);
        log.info("Cart created with ID: {}", saved.getId().getValue());
        return saved;
    }

    @Transactional(readOnly = true)
    public Cart getCart(GetCartQuery query) {
        return cartRepository.findById(CartId.of(query.getCartId()))
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    @Transactional
    public Cart addToCart(AddToCartCommand command) {
        log.info("Adding product {} to cart {}", command.getProductId(), command.getCartId());

        Cart cart = getCart(new GetCartQuery(command.getCartId()));

        Product product = productRepository.findById(ProductId.of(command.getProductId()))
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        cartDomainService.validateStockForCart(cart, product, command.getQuantity());

        cart.addItem(
                product.getId(),
                product.getName(),
                product.getPrice(),
                command.getQuantity()
        );

        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeFromCart(RemoveFromCartCommand command) {
        log.info("Removing item {} from cart {}", command.getCartItemId(), command.getCartId());

        Cart cart = getCart(new GetCartQuery(command.getCartId()));
        cart.removeItem(command.getCartItemId());
        return cartRepository.save(cart);
    }

    @Transactional
    public void clearCart(ClearCartCommand command) {
        log.info("Clearing cart {}", command.getCartId());

        Cart cart = getCart(new GetCartQuery(command.getCartId()));
        cart.clear();
        cartRepository.save(cart);
    }
}
