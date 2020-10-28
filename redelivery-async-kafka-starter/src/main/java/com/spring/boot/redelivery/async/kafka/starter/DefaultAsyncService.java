package com.spring.boot.redelivery.async.kafka.starter;


import brave.Span;
import brave.Tracer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spring.boot.redelivery.api.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

import static com.spring.boot.redelivery.api.log.LogUtils.*;


@RequiredArgsConstructor
public class DefaultAsyncService<Req, Res> implements AsyncService<Req>, Redelivery {
    private final DataProvider<Req, Res> dataProvider;
    private final RedeliveryService redeliveryService;
    private final int redeliveryCount;
    private final long secondToActivate;
    private final Callback<Req, Res> callback;
    @Getter
    private final String system;
    private final Class<Req> clazz;
    @Autowired
    private Tracer tracer;

    @KafkaListener(topics = "#{__listener.getSystem()}")
    public void onMessage(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        Context<Req> context = new Gson().fromJson(record.value(), TypeToken.getParameterized(Context.class, clazz).getType());
        send(context);
        acknowledgment.acknowledge();
    }

    @Override
    public void send(Context<Req> context) {
        context.setSystem(system);
        Span newSpan = tracer.nextSpan().name(system).start();
        context.setCount(context.getCount() + 1);
        if (context.getCount() < redeliveryCount) {
            Req request = context.getRequest();
            long startTime = logBefore(context);
            try (Tracer.SpanInScope ws = tracer.withSpanInScope(newSpan.start())) {
                Res response = dataProvider.invoke(request);
                logAfter(startTime, context, response);
                callback.onSuccess(response, context);
            } catch (RedeliveryException e) {
                logError(startTime, context, e.getMessage());
                doDelivery(context);
            } catch (NonRedeliveryException e) {
                logError(startTime, context, e.getMessage());
                callback.onFail(context);
                doDeadDelivery(context);
            } finally {
                newSpan.finish();
            }
        } else {
            callback.onFail(context);
            doDeadDelivery(context);
        }
    }

    @Override
    public void doDelivery(Context<?> context) {
        redeliveryService.doDelivery(context, secondToActivate);
    }

    @Override
    public void doDeadDelivery(Context<?> context) {
        redeliveryService.doDeadDelivery(context);
    }
}
