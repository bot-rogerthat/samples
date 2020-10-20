package com.spring.boot.rest.service.redelivery;

public interface SyncService<Req, Res> {

    Res send(Context<Req> context) throws RedeliveryException, NonRedeliveryException;
}
