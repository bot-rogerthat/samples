package com.spring.boot.redelivery.starter;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

@Slf4j
@RequiredArgsConstructor
public class DefaultAsyncService<Req, Res> implements AsyncService<Req>, Redelivery {
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
    @KafkaListener(topics = "#{__listener.getSystem()}")
    public void onMessage(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
        String message = consumerRecord.value();
        Context<Req> context = new Gson().fromJson(message, TypeToken.getParameterized(Context.class, clazz).getType());
        send(context);
        acknowledgment.acknowledge();
    }

    @Override
    public void send(Context<Req> context) {
        Span span = tracer.buildSpan(system).start();
        context.setCount(context.getCount() + 1);
        if (context.getCount() <= redeliveryCount) {
            Req request = context.getRequest();
            try (Scope scope = tracer.scopeManager().activate(span)) {
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
                span.finish();
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
