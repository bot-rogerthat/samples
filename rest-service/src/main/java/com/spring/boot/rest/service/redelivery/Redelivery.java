package com.spring.boot.rest.service.redelivery;

public interface Redelivery {

    void doDelivery(Context<?> context, long secondToActivate);
}
