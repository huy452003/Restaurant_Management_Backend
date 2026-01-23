package com.handle_excepitons;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.ToString;
import java.util.List;


@Data
@AllArgsConstructor
@ToString
public class ValidationExceptionHandle extends RuntimeException {
    private final List<String> invalidFields;
    private final String modelName;

    public ValidationExceptionHandle(String message, List<String> invalidFields, String modelName) {
        super(message);
        this.invalidFields = invalidFields;
        this.modelName = modelName;
    }

}
