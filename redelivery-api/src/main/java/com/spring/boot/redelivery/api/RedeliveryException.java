package com.spring.boot.redelivery.api;

public class RedeliveryException extends Exception {

    public RedeliveryException(Throwable cause) {
        super(cause);
    }
}
