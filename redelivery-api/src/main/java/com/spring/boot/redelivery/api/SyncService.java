package com.spring.boot.redelivery.api;

public interface SyncService<Req, Res> {

    Res send(Context<Req> context) throws RedeliveryException, NonRedeliveryException;
}
