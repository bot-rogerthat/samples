package com.spring.boot.redelivery.api;

public interface Callback<Req, Res> {

    void onSuccess(Res response, Context<Req> context);

    void onFail(Context<Req> context);
}
