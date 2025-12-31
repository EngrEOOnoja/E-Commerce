// ResourceNotFoundException.java
package com.ecommerce.backend.domain.shared.exception;

public class ResourceNotFoundException extends DomainException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}