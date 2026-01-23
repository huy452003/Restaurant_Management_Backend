package com.handle_excepitons;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ServiceUnavailableExceptionHandle extends RuntimeException {
    private final String serviceName;

    public ServiceUnavailableExceptionHandle(String message, String serviceName) {
        super(message);
        this.serviceName = serviceName;
    }
}
