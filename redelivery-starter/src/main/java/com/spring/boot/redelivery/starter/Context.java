package com.spring.boot.redelivery.starter;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class Context<Req> {
    private String appName;
    private String uuid;
    private String system;
    private int count;
    private Req request;
    private Map<String, String> storage;

    public Context(Context<?> context, Req request) {
        this.appName = context.getAppName();
        this.uuid = context.getUuid();
        this.request = request;
        this.storage = context.getStorage();
    }

    public Context(String appName, Req request) {
        this.appName = appName;
        this.uuid = UUID.randomUUID().toString();
        this.request = request;
        this.storage = new HashMap<>();
    }

    // for continue process
    public Context(String appName, String uuid, String system, Req request) {
        this.appName = appName;
        this.uuid = uuid;
        this.system = system;
        this.request = request;
        this.storage = new HashMap<>();
    }

    public void put(String key, String value) {
        storage.put(key, value);
    }

    public String get(String key) {
        return storage.get(key);
    }
}
