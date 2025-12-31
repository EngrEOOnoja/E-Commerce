// BusinessRuleViolationException.java
package com.ecommerce.backend.domain.shared.exception;

public class BusinessRuleViolationException extends DomainException {
    public BusinessRuleViolationException(String message) {
        super(message);
    }
}
