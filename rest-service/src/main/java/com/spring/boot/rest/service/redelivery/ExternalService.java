package com.spring.boot.rest.service.redelivery;

public interface ExternalService<Req> {

    void send(Context<Req> context);
}
