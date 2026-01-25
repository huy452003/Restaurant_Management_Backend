package com.handle_exceptions;

import java.util.List;
import java.util.ArrayList;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class NotFoundExceptionHandle extends RuntimeException {
    private final List<Object> notFounds;
    private final String modelName;

    public NotFoundExceptionHandle(String message, List<?> notFounds, String modelName) {
        super(message);
        this.notFounds = notFounds != null ? new ArrayList<>(notFounds) : new ArrayList<>();
        this.modelName = modelName;
    }
}
