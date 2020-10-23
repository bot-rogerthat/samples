package com.spring.boot.redelivery.starter;


import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@RequiredArgsConstructor
public class DefaultSyncService<Req, Res> implements SyncService<Req, Res> {
    private final DataProvider<Req, Res> dataProvider;
    private final String system;
    @Autowired
    private Tracer tracer;

    @Override
    public Res send(Context<Req> context) throws NonRedeliveryException {
        Span span = tracer.buildSpan(system).start();
        try (Scope scope = tracer.scopeManager().activate(span)) {
            log.info("uuid: {}, system: {}, call: {}, data: {}", context.getUuid(), system, "before", context.getRequest());
            Res response = dataProvider.invoke(context.getRequest());
            log.info("uuid: {}, system: {}, call: {}, data: {}", context.getUuid(), system, "after", response);
            return response;
        } catch (Exception e) {
            log.info("uuid: {}, system: {}, call: {}, data: {}", context.getUuid(), system, "error", e.getMessage());
            throw new NonRedeliveryException(e);
        } finally {
            span.finish();
        }
    }

    public String getSystem() {
        return system;
    }
}
