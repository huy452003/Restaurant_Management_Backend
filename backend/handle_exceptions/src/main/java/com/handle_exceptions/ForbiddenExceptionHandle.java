package com.handle_exceptions;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ForbiddenExceptionHandle extends RuntimeException {
    private final String modelName;
    private final String requiredRole;

    public ForbiddenExceptionHandle(String message, String modelName, String requiredRole) {
        super(message);
        this.modelName = modelName;
        this.requiredRole = requiredRole;
    }
}


