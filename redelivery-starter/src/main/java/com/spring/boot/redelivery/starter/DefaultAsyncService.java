package com.spring.boot.redelivery.starter;


import brave.Span;
import brave.Tracer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class DefaultAsyncService<Req, Res> implements AsyncService<Req>, Redelivery, MessageListener {
    private final DataProvider<Req, Res> dataProvider;
    private final RedeliveryService redeliveryService;
    private final int redeliveryCount;
    private final long secondToActivate;
    private final Callback<Req, Res> callback;
    private final String system;
    private final Class<Req> clazz;
    @Autowired
    private Tracer tracer;

    @Override
    public void onMessage(Message message) {
        Context<Req> context = new Gson().fromJson(new String(message.getBody(), StandardCharsets.UTF_8), TypeToken.getParameterized(Context.class, clazz).getType());
        send(context);
    }

    @Override
    public void send(Context<Req> context) {
        Span newSpan = tracer.nextSpan().name(system).start();
        context.setCount(context.getCount() + 1);
        if (context.getCount() < redeliveryCount) {
            Req request = context.getRequest();
            try (Tracer.SpanInScope ws = tracer.withSpanInScope(newSpan.start())) {
                //todo before log
                log.info("uuid: {}, system: {}, call: {}, data: {}", context.getUuid(), system, "before", context.getRequest());
                Res response = dataProvider.invoke(request);
                //todo after log
                log.info("uuid: {}, system: {}, call: {}, data: {}", context.getUuid(), system, "after", response);
                callback.onSuccess(response, context);
            } catch (RedeliveryException e) {
                //todo error after log
                log.info("uuid: {}, system: {}, call: {}, data: {}", context.getUuid(), system, "error", e.getMessage());
                doDelivery(context, secondToActivate);
            } catch (NonRedeliveryException e) {
                //todo error after log
                log.info("uuid: {}, system: {}, call: {}, data: {}", context.getUuid(), system, "error", e.getMessage());
                callback.onFail(context);
            } finally {
                newSpan.finish();
            }
        } else {
            callback.onFail(context);
        }
    }

    @Override
    public void doDelivery(Context<?> context, long secondToActivate) {
        redeliveryService.doDelivery(context, secondToActivate);
    }

    public String getSystem() {
        return system;
    }
}
