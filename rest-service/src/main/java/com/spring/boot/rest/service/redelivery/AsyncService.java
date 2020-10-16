package com.spring.boot.rest.service.redelivery;

public interface AsyncService<Req, Res> {

    void onMessage(String message);

    void send(Context<Req> context);

    void onSuccess(Res response, Context<Req> context);

    void onFail(Context<Req> context);
}
