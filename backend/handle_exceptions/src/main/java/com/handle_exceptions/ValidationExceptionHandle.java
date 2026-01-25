package com.handle_exceptions;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@ToString
public class ValidationExceptionHandle extends RuntimeException {
    private final List<Object> invalidFields;
    private final String modelName;

    public ValidationExceptionHandle(String message, List<?> invalidFields, String modelName) {
        super(message);
        this.invalidFields = invalidFields != null ? new ArrayList<>(invalidFields) : new ArrayList<>();
        this.modelName = modelName;
    }

}
