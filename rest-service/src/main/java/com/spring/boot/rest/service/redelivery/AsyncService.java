package com.spring.boot.rest.service.redelivery;

public interface AsyncService<Req, Res> extends ExternalService<Req> {

    void onMessage(String message);

    void onSuccess(Res response, Context<Req> context);

    void onFail(Context<Req> context);
}
