package com.spring.boot.redelivery.sync.starter;


import brave.Span;
import brave.Tracer;
import com.spring.boot.redelivery.api.Context;
import com.spring.boot.redelivery.api.DataProvider;
import com.spring.boot.redelivery.api.NonRedeliveryException;
import com.spring.boot.redelivery.api.SyncService;
import com.spring.boot.redelivery.api.log.LogUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

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
        long startTime = LogUtils.logBefore(context);
        try (Tracer.SpanInScope spanInScope = tracer.withSpanInScope(newSpan.start())) {
            Res response = dataProvider.invoke(context.getRequest());
            LogUtils.logAfter(startTime, context, response);
            return response;
        } catch (Exception e) {
            LogUtils.logError(startTime, context, e.getMessage());
            throw new NonRedeliveryException(e);
        } finally {
            newSpan.finish();
        }
    }

    public String getSystem() {
        return system;
    }
}
