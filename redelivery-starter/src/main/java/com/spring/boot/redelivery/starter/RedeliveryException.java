package com.spring.boot.redelivery.starter;

public class RedeliveryException extends Exception {

    public RedeliveryException(Throwable cause) {
        super(cause);
    }
}
