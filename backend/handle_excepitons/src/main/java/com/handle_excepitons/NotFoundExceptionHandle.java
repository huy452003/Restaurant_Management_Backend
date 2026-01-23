package com.handle_excepitons;

import java.util.List;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class NotFoundExceptionHandle extends RuntimeException {
    private final List<String> notFounds;
    private final String modelName;

    public NotFoundExceptionHandle(String message, List<String> notFounds, String modelName) {
        super(message);
        this.notFounds = notFounds;
        this.modelName = modelName;
    }
}
