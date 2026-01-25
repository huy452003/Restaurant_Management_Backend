package com.handle_exceptions;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class TooManyRequestsExceptionHandle extends RuntimeException {
    private final String serviceName;
    private final Long retryAfter;

    public TooManyRequestsExceptionHandle(String message, String serviceName, Long retryAfter) {
        super(message);
        this.serviceName = serviceName;
        this.retryAfter = retryAfter;
    }
}
