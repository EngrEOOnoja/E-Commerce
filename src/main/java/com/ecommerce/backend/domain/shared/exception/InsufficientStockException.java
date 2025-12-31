// InsufficientStockException.java
package com.ecommerce.backend.domain.shared.exception;

public class InsufficientStockException extends DomainException {
    public InsufficientStockException(String message) {
        super(message);
    }
}
