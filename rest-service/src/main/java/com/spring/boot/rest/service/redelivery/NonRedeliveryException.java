package com.spring.boot.rest.service.redelivery;

public class NonRedeliveryException extends Exception {
    public NonRedeliveryException(Throwable cause) {
        super(cause);
    }
}
