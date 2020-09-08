package com.spring.boot.rest.service.common.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime unmarshal(String s) {
        if (Objects.nonNull(s)) {
            try {
                return LocalDateTime.parse(s, DATE_TIME_FORMATTER);
            } catch (DateTimeParseException e) {
                throw new RuntimeException("Failed to parse localDateTime: " + s, e);
            }
        }
        return null;
    }

    @Override
    public String marshal(LocalDateTime localDateTime) {
        if (Objects.nonNull(localDateTime)) {
            return localDateTime.format(DATE_TIME_FORMATTER);
        }
        return null;
    }
}
