package com.spring.boot.rest.service.redelivery;

public interface SyncService<Req, Res> extends ExternalService<Req> {

    void onSuccess(Res response, Context<Req> context);

    void onFail(Context<Req> context);
}
