package com.purplelove.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Logger {

    public enum Level {
        INFO, WARN, ERROR, DEBUG
    }

    private final String name;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            .withZone(ZoneId.systemDefault());

    public Logger(String name) {
        this.name = name;
    }

    public static Logger getLogger(Class<?> clazz) {
        return new Logger(clazz.getSimpleName());
    }

    public void info(String message) {
        log(Level.INFO, message);
    }

    public void warn(String message) {
        log(Level.WARN, message);
    }

    public void error(String message) {
        log(Level.ERROR, message);
    }

    public void error(String message, Throwable throwable) {
        log(Level.ERROR, message + " - " + throwable.getMessage());
        throwable.printStackTrace();
    }

    public void debug(String message) {
        log(Level.DEBUG, message);
    }

    private void log(Level level, String message) {
        String timestamp = FORMATTER.format(Instant.now());
        System.out.printf("[%s] [%s] [%s] %s%n", timestamp, level, name, message);
    }
}
