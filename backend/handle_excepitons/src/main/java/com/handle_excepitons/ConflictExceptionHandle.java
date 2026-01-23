package com.handle_excepitons;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.util.List;
import lombok.ToString; 

@Data
@AllArgsConstructor
@ToString
public class ConflictExceptionHandle extends RuntimeException {
    private final List<String> conflicts;
    private final String modelName;

    public ConflictExceptionHandle(String message, List<String> conflicts, String modelName) {
        super(message);
        this.conflicts = conflicts;
        this.modelName = modelName;
    }
}
