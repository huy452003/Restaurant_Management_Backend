package com.logging.services;

import com.logging.models.LogContext;

public interface LoggingService {
    void logInfo(String message, LogContext logContext);
    void logWarn(String message, LogContext logContext);
    void logDebug(String message, LogContext logContext);
    void logError(String message, Exception exception, LogContext logContext);

}
