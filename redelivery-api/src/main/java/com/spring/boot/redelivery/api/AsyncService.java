package com.spring.boot.redelivery.api;

public interface AsyncService<Req> {

    void send(Context<Req> context);
}
