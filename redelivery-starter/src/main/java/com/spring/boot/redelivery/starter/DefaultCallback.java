package com.spring.boot.redelivery.starter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultCallback<Req, Res> implements Callback<Req, Res> {

    @Override
    public void onSuccess(Res response, Context<Req> context) {
        // do nothing
    }

    @Override
    public void onFail(Context<Req> context) {
        // do nothing
    }
}
