package com.spring.boot.redelivery.starter.log;

import com.spring.boot.redelivery.starter.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.spring.boot.redelivery.starter.log.LogUtils.Location.AFTER;
import static com.spring.boot.redelivery.starter.log.LogUtils.Location.ERROR;

public final class LogUtils {
    public static final String MESSAGE = "uuid: {}, system: {}, call: {}, data: {}";
    public static final String MESSAGE_WITH_TIME = "uuid: {}, system: {}, call: {}, data: {}, time: {} ms";

    private LogUtils() {
    }

    public static long logStart(String methodName, Context<?> context) {
        Logger logger = LoggerFactory.getLogger(context.getAppName());
        logger.info(MESSAGE, context.getUuid(), methodName, Location.START.getName(), context.getRequest());
        return System.nanoTime();
    }

    public static void logEnd(long startTime, String methodName, Context<?> context, Object data) {
        long time = getTime(startTime);
        Logger logger = LoggerFactory.getLogger(context.getAppName());
        logger.info(MESSAGE_WITH_TIME, context.getUuid(), methodName, Location.END.getName(), data, time);
    }

    public static long logBefore(Context<?> context) {
        Logger logger = LoggerFactory.getLogger(context.getAppName());
        logger.info(MESSAGE, context.getUuid(), context.getSystem(), Location.BEFORE.getName(), context.getRequest());
        return System.nanoTime();
    }

    public static void logAfter(long startTime, Context<?> context, Object data) {
        long time = getTime(startTime);
        logWithTime(AFTER, time, context, data);
    }

    public static void logError(long startTime, Context<?> context, Object data) {
        long time = getTime(startTime);
        logWithTime(ERROR, time, context, data);
    }

    private static void logWithTime(Location location, long time, Context<?> context, Object data) {
        Logger logger = LoggerFactory.getLogger(context.getAppName());
        logger.info(MESSAGE_WITH_TIME, context.getUuid(), context.getSystem(), location.getName(), data, time);
    }

    private static long getTime(long startTime) {
        long stopTime = System.nanoTime();
        return (stopTime - startTime) / 1_000_000; // ms
    }

    enum Location {
        START("start"),
        END("end"),
        BEFORE("before"),
        AFTER("after"),
        ERROR("error");

        private final String name;

        Location(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
