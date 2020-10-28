package com.spring.boot.redelivery.starter;


import brave.Span;
import brave.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import static com.spring.boot.redelivery.starter.log.LogUtils.*;

@RequiredArgsConstructor
public class DefaultSyncService<Req, Res> implements SyncService<Req, Res> {
    private final DataProvider<Req, Res> dataProvider;
    private final String system;
    @Autowired
    private Tracer tracer;

    @Override
    public Res send(Context<Req> context) throws NonRedeliveryException {
        context.setSystem(system);
        Span newSpan = tracer.nextSpan().name(system).start();
        long startTime = logBefore(context);
        try (Tracer.SpanInScope spanInScope = tracer.withSpanInScope(newSpan.start())) {
            Res response = dataProvider.invoke(context.getRequest());
            logAfter(startTime, context, response);
            return response;
        } catch (Exception e) {
            logError(startTime, context, e.getMessage());
            throw new NonRedeliveryException(e);
        } finally {
            newSpan.finish();
        }
    }

    public String getSystem() {
        return system;
    }
}
