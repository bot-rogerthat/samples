package com.spring.boot.redelivery.starter;

public interface AsyncService<Req> {

    void onMessage(String message);

    void send(Context<Req> context);
}
