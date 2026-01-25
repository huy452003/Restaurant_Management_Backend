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
            "[module: {} - className: {} - methodName: {} - userId: {} - message: {}]",
            context.getModule(),
            context.getClassName(),
            context.getMethodName(),
            context.getUserId() != null ? String.valueOf(context.getUserId()) : "N/A",
            message
        );
    }
    
    @Override
    public void logWarn(String message, LogContext context) {
        log.warn(
            "[module: {} - className: {} - methodName: {} - userId: {} - message: {}]",
            context.getModule(),
            context.getClassName(),
            context.getMethodName(),
            context.getUserId() != null ? String.valueOf(context.getUserId()) : "N/A",
            message
        );
    }
    
    @Override
    public void logDebug(String message, LogContext context) {
        log.debug(
            "[module: {} - className: {} - methodName: {} - userId: {} - message: {}]",
            context.getModule(),
            context.getClassName(),
            context.getMethodName(),
            context.getUserId() != null ? String.valueOf(context.getUserId()) : "N/A",
            message
        );
    }

    @Override
    public void logError(String message, LogContext context, Exception exception) {
        String exceptionInfo = exception != null 
            ? exception.getClass().getSimpleName() + ": " + (exception.getMessage() != null ? exception.getMessage() : "No message")
            : "No exception";
        log.error(
            "[module: {} - className: {} - methodName: {} - userId: {} - message: {} - exception: {}]",
            context.getModule(),
            context.getClassName(),
            context.getMethodName(),
            context.getUserId() != null ? String.valueOf(context.getUserId()) : "N/A",
            message,
            exceptionInfo
        );
    }

}
