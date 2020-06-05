package ru.xander.etl.graph.util;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Alexander Shakhov
 */
public final class Utils {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static Integer parseInt(String value) {
        return parseInt(value, null);
    }

    public static Integer parseInt(String value, Integer defaultValue) {
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return DATE_TIME_FORMATTER.format(dateTime);
    }

    public static LocalDateTime parseDateTime(String dateTime) {
        if (StringUtils.isEmpty(dateTime)) {
            return null;
        }
        return LocalDateTime.from(DATE_TIME_FORMATTER.parse(dateTime));
    }

    public static <T> T nvl(T value, T defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return value;
    }
}
