package com.handle_exceptions;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UnauthorizedExceptionHandle extends RuntimeException {
    private final String modelName;
    
    public UnauthorizedExceptionHandle(String message, String modelName) {
        super(message);
        this.modelName = modelName;
    }

}

