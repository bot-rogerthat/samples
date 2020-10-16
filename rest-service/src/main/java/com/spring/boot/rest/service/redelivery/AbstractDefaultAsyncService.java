package com.spring.boot.rest.service.redelivery;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spring.boot.rest.service.common.RedeliveryService;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class AbstractDefaultAsyncService<Req, Res> implements AsyncService<Req, Res> {
    private final DataProvider<Req, Res> dataProvider;
    private final int redeliveryCount;
    @Autowired
    private RedeliveryService redeliveryService;

    public AbstractDefaultAsyncService(DataProvider<Req, Res> dataProvider, int redeliveryCount) {
        this.dataProvider = dataProvider;
        this.redeliveryCount = redeliveryCount;
    }

    @Override
    public void onMessage(String message) {
        Context<Req> context = new Gson().fromJson(message, new TypeToken<Req>() {
        }.getType());
        send(context);
    }

    @Override
    public void send(Context<Req> context) {
        if (context.getCount() < redeliveryCount) {
            try {
                //todo before log
                dataProvider.invoke(context.getRequest());
                //todo after log
            } catch (RedeliveryException e) {
                //todo error after log
                redeliveryService.doDelivery(context);
            } catch (NonRedeliveryException e) {
                //todo error after log
                onFail(context);
            }
        } else {
            onFail(context);
        }
    }

    @Override
    public void onFail(Context<Req> context) {
        redeliveryService.doDelivery(context);
    }

    @Override
    public void onSuccess(Res response, Context<Req> context) {
        //do nothing
    }
}
