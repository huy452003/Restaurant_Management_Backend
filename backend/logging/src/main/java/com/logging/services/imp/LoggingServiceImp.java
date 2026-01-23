package com.logging.services.imp;

import com.logging.models.LogContext;
import com.logging.services.LoggingService;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class LoggingServiceImp implements LoggingService {
    
    @Override
    public void logInfo(String message, LogContext context) {
        log.info(
            "[{}] {} - {}",
            context.module(),
            context.className(),
            message
        );
    }
    
    @Override
    public void logWarn(String message, LogContext context) {
        log.warn(
            "[{}] {} - {}",
            context.module(),
            context.className(),
            message
        );
    }
    
    @Override
    public void logDebug(String message, LogContext context) {
        log.debug(
            "[{}] {} - {}",
            context.module(),
            context.className(),
            message
        );
    }

    @Override
    public void logError(String message, Exception exception, LogContext context) {
        log.error(
            "[{}] {} - {}: {}",
            context.module(),
            context.className(),
            message,
            exception != null ? exception.getMessage() : "No exception");
    }

}
