package com.spring.boot.redelivery.starter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

import java.lang.reflect.Type;

@Slf4j
public class DefaultAsyncService<Req, Res> implements AsyncService<Req>, Redelivery {
    private final DataProvider<Req, Res> dataProvider;
    private final RedeliveryService redeliveryService;
    private final int redeliveryCount;
    private final long secondToActivate;
    private final Callback<Req, Res> callback;
    private final String topic;
    private final Class<Req> clazz;

    public DefaultAsyncService(DataProvider<Req, Res> dataProvider,
                               RedeliveryService redeliveryService,
                               int redeliveryCount,
                               long secondToActivate,
                               Callback<Req, Res> callback,
                               String topic,
                               Class<Req> clazz) {
        this.dataProvider = dataProvider;
        this.redeliveryService = redeliveryService;
        this.redeliveryCount = redeliveryCount;
        this.secondToActivate = secondToActivate;
        this.callback = callback;
        this.topic = topic;
        this.clazz = clazz;
    }

    @Override
    @KafkaListener(topics = "#{__listener.getTopic()}")
    public void onMessage(String message) {
        Context<Req> context = new Gson().fromJson(message, TypeToken.getParameterized(Context.class, clazz).getType());
        send(context);
    }

    @Override
    public void send(Context<Req> context) {
        context.setCount(context.getCount() + 1);
        if (context.getCount() < redeliveryCount) {
            Req request = context.getRequest();
            try {
                //todo before log
                log.info("before dataProvider.invoke {}", request);
                Res response = dataProvider.invoke(request);
                //todo after log
                log.info("after dataProvider.invoke {}", request);

                callback.onSuccess(response, context);
            } catch (RedeliveryException e) {
                //todo error after log
                log.info("error dataProvider.invoke {}", request);
                doDelivery(context, secondToActivate);
            } catch (NonRedeliveryException e) {
                //todo error after log
                log.info("error dataProvider.invoke {}", request);
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
