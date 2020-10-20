package com.spring.boot.rest.service.redelivery;

public interface AsyncService<Req> {

    void onMessage(String message);

    void send(Context<Req> context);
}
