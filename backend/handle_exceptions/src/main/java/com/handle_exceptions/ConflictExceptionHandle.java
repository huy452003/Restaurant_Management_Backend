package com.handle_exceptions;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.ToString; 

import java.util.List;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@ToString
public class ConflictExceptionHandle extends RuntimeException {
    private final List<Object> conflicts;
    private final String modelName;

    public ConflictExceptionHandle(String message, List<?> conflicts, String modelName) {
        super(message);
        this.conflicts = conflicts != null ? new ArrayList<>(conflicts) : new ArrayList<>();
        this.modelName = modelName;   
    }
}
