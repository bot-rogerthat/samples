package com.spring.boot.redelivery.api;

public interface Redelivery {

    void doDelivery(Context<?> context);

    void doDeadDelivery(Context<?> context);
}
