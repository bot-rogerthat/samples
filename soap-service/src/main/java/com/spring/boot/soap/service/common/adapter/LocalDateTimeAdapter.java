package com.spring.boot.soap.service.common.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    @Override
    public LocalDateTime unmarshal(String value) {
        if (Objects.nonNull(value)) {
            try {
                return LocalDateTime.parse(value);
            } catch (DateTimeParseException e) {
                throw new RuntimeException("Failed to parse localDateTime: " + value, e);
            }
        }
        return null;
    }

    @Override
    public String marshal(LocalDateTime localDateTime) {
        if (Objects.nonNull(localDateTime)) {
            return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return null;
    }
}
