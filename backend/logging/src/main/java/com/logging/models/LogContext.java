package com.logging.models;

public record LogContext(
    String module, 
    String className, 
    String methodName, 
    String userId, 
    String message
) {}
