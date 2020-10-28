package com.spring.boot.redelivery.api;

public interface DataProvider<Req, Res> {

    Res invoke(Req request) throws RedeliveryException, NonRedeliveryException;
}
