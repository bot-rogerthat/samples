package com.spring.boot.redelivery.starter;

public interface Redelivery {

    void doDelivery(Context<?> context, long secondToActivate);
}