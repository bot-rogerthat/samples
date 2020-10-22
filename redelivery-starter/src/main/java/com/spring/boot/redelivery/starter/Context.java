package com.spring.boot.redelivery.starter;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class Context<Req> {
    private String uuid;
    private String serviceId;
    private int count;
    private Req request;
    private Map<String, String> storage;

    public Context(Context<?> context, String serviceId, Req request) {
        this.uuid = context.getUuid();
        this.serviceId = serviceId;
        this.request = request;
        this.storage = context.getStorage();
    }

    public Context(Req request) {
        this.uuid = UUID.randomUUID().toString();
        this.request = request;
        this.serviceId = "api";
        this.storage = new HashMap<>();
    }

    public void put(String key, String value) {
        storage.put(key, value);
    }

    public String get(String key) {
        return storage.get(key);
    }
}
