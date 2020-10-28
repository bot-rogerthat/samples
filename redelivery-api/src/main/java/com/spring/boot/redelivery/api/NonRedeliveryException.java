package com.spring.boot.redelivery.api;

public class NonRedeliveryException extends Exception {

    public NonRedeliveryException(Throwable cause) {
        super(cause);
    }
}
