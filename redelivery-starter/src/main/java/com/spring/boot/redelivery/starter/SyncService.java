package com.spring.boot.redelivery.starter;

public interface SyncService<Req, Res> {

    Res send(Context<Req> context) throws RedeliveryException, NonRedeliveryException;
}
