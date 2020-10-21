package com.spring.boot.redelivery.starter;

public class NonRedeliveryException extends Exception {
    public NonRedeliveryException(Throwable cause) {
        super(cause);
    }
}
