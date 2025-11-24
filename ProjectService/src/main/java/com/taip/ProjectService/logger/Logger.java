package com.taip.ProjectService.logger;

public class Logger {
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(Logger.class);

    public static void logInfo(String message) {
        LOG.info(message);
    }

    public static void logError(String message, Throwable throwable) {
        LOG.error(message, throwable);
    }

    public static void logDebug(String message) {
        LOG.debug(message);
    }
}
