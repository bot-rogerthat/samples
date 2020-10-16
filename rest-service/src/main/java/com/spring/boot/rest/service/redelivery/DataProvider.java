package com.spring.boot.rest.service.redelivery;

public interface DataProvider<Req, Res> {

    Res invoke(Req request) throws RedeliveryException, NonRedeliveryException;
}
