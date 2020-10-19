package com.spring.boot.rest.service.redelivery;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spring.boot.rest.service.common.RedeliveryService;
import org.springframework.beans.factory.annotation.Autowired;


public class DefaultAsyncService<Req, Res> implements AsyncService<Req, Res>, Redelivery {
    private final DataProvider<Req, Res> dataProvider;
    private final int redeliveryCount;
    private final long secondToActivate;
    @Autowired
    private RedeliveryService redeliveryService;

    public DefaultAsyncService(DataProvider<Req, Res> dataProvider, int redeliveryCount, long secondToActivate) {
        this.dataProvider = dataProvider;
        this.redeliveryCount = redeliveryCount;
        this.secondToActivate = secondToActivate;
    }

    @Override
    public void onMessage(String message) {
        Context<Req> context = new Gson().fromJson(message, new TypeToken<Context<Req>>() {
        }.getType());
        send(context);
    }

    @Override
    public void send(Context<Req> context) {
        if (context.getCount() < redeliveryCount) {
            try {
                //todo before log
                Res response = dataProvider.invoke(context.getRequest());
                onSuccess(response, context);
                //todo after log
            } catch (RedeliveryException e) {
                //todo error after log
                doDelivery(context, secondToActivate);
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
        doDelivery(context, secondToActivate);
    }

    @Override
    public void onSuccess(Res response, Context<Req> context) {
        //do nothing
    }

    @Override
    public void doDelivery(Context<?> context, long secondToActivate) {
        redeliveryService.doDelivery(context, secondToActivate);
    }
}
