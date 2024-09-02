package com.compass.reinan.api_ecommerce.util;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtils {
    public static final DateTimeFormatter ISO_8601_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static String formatToISO8601(Instant instant) {
        return instant.atZone(ZoneId.systemDefault()).format(ISO_8601_FORMATTER);
    }
}
