package com.spring.boot.rest.service.redelivery;

public interface Callback<Req, Res> {

    void onSuccess(Res response, Context<Req> context);

    void onFail(Context<Req> context);
}
