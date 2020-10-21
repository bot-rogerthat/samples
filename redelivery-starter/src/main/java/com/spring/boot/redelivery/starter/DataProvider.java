package com.spring.boot.redelivery.starter;

public interface DataProvider<Req, Res> {

    Res invoke(Req request) throws RedeliveryException, NonRedeliveryException;
}
