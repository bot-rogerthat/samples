package com.spring.boot.redelivery.starter;

public interface AsyncService<Req> {

    void send(Context<Req> context);
}
