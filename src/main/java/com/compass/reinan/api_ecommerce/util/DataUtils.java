package com.compass.reinan.api_ecommerce.util;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class DataUtils {
    public final DateTimeFormatter ISO_8601_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public String formatToISO8601(Instant instant) {
        return instant.atZone(ZoneId.systemDefault()).format(ISO_8601_FORMATTER);
    }
}
