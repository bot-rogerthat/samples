package com.spring.boot.redelivery.starter;

public interface Callback<Req, Res> {

    default void onSuccess(Res response, Context<Req> context) {
        // do nothing
    }

    default void onFail(Context<Req> context) {
        // do nothing
    }
}
