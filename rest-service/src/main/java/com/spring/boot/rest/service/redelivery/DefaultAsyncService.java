package com.spring.boot.rest.service.redelivery;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spring.boot.rest.service.common.RedeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;


public class DefaultAsyncService<Req, Res> implements AsyncService<Req>, Redelivery {
    private final DataProvider<Req, Res> dataProvider;
    private final int redeliveryCount;
    private final long secondToActivate;
    private final Callback<Req, Res> callback;
    private final String topic;
    @Autowired
    private RedeliveryService redeliveryService;

    public DefaultAsyncService(DataProvider<Req, Res> dataProvider,
                               int redeliveryCount,
                               long secondToActivate,
                               Callback<Req, Res> callback,
                               String topic) {
        this.dataProvider = dataProvider;
        this.redeliveryCount = redeliveryCount;
        this.secondToActivate = secondToActivate;
        this.callback = callback;
        this.topic = topic;
    }

    @Override
    @KafkaListener(topics = "#{__listener.getTopic()}")
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
                //todo after log
                callback.onSuccess(response, context);
            } catch (RedeliveryException e) {
                //todo error after log
                doDelivery(context, secondToActivate);
            } catch (NonRedeliveryException e) {
                //todo error after log
                callback.onFail(context);
            }
        } else {
            callback.onFail(context);
        }
    }

    @Override
    public void doDelivery(Context<?> context, long secondToActivate) {
        redeliveryService.doDelivery(context, secondToActivate);
    }

    public String getTopic() {
        return topic;
    }
}
