package com.common.models;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Response<T>(
    Integer statusCode,
    String message,
    String modelName,
    Map<String, String> errors,
    T data
) {}
