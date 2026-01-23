package com.handle_excepitons;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.common.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import java.util.Locale;
import org.springframework.context.i18n.LocaleContextHolder;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

@RestControllerAdvice
public class CustomExceptionHandler {
    @Autowired
    MessageSource messageSource;
    
    // Not Found Exception Handler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundExceptionHandle.class)
    ResponseEntity<Response<?>> notFoundExceptionHandle(NotFoundExceptionHandle e) {
        Locale locale = LocaleContextHolder.getLocale();

        Map<String, String> errors = new HashMap<>();
        errors.put("Error", "List of errors: " + e.getNotFounds().toString());

        Response<?> response = new Response<>(
            404,
            messageSource.getMessage("response.error.notFoundError", null, locale),
            e.getModelName(),
            errors,
            null
        );
        return ResponseEntity.status(response.statusCode()).body(response);
    }

    // Conflict Exception Handler
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictExceptionHandle.class)
    ResponseEntity<Response<?>> conflictExceptionHandle(ConflictExceptionHandle e) {
        Locale locale = LocaleContextHolder.getLocale();

        Map<String, String> errors = new HashMap<>();
        errors.put("Error", "List of errors: " + e.getConflicts().toString());

        Response<?> response = new Response<>(
            409,
            messageSource.getMessage("response.error.conflictError", null, locale),
            e.getModelName(),
            errors,
            null
        );
        return ResponseEntity.status(response.statusCode()).body(response);
    }

    // Body Validation Exception Handler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Response<?>> handleBodyValidation(MethodArgumentNotValidException e) {
        Locale locale = LocaleContextHolder.getLocale();

        Map<String, String> errors = new LinkedHashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            String key = error.getDefaultMessage();
            if(key != null && key.startsWith("{") && key.endsWith("}")){
                key = key.substring(1, key.length() - 1);
            }
            String msg = messageSource.getMessage(
                key != null ? key : error.getField(), null, key != null ? key : error.getField(), locale
            );
            String fieldName = error.getField();
            if(fieldName.contains(".")){
                fieldName = fieldName.substring(fieldName.lastIndexOf(".") + 1);
            }
            errors.put(fieldName, msg);
        });

        Response<?> response = new Response<>(
            400,
            messageSource.getMessage("response.error.validateFailed", null, locale),
            null,
            errors,
            null
        );
        return ResponseEntity.status(response.statusCode()).body(response);
    }

    // Validation Exception Handler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationExceptionHandle.class)
    ResponseEntity<Response<?>> validationExceptionHandle(ValidationExceptionHandle e) {
        Locale locale = LocaleContextHolder.getLocale();

        Map<String, String> errors = new HashMap<>();
        errors.put("Error", e.getMessage());

        if(e.getInvalidFields() != null && !e.getInvalidFields().isEmpty()){
            errors.put("InvalidFields", e.getInvalidFields().toString());
        }

        Response<?> response = new Response<>(
            400,
            messageSource.getMessage("response.error.validateFailed", null, locale),
            e.getModelName() != null ? e.getModelName() : "Request-Model",
            errors,
            null
        );
        return ResponseEntity.status(response.statusCode()).body(response);
    }

    // Invalid Format Exception Handler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response<?>> handleInvalidFormat(HttpMessageNotReadableException ex) {
        Locale locale = LocaleContextHolder.getLocale();

        Map<String, String> errors = new HashMap<>();
        Throwable cause = ex.getMostSpecificCause();

        if (cause instanceof InvalidFormatException || cause instanceof MismatchedInputException) {
            List<JsonMappingException.Reference> path = ((JsonMappingException) cause).getPath();
            if (!path.isEmpty()) {
                String field = path.get(path.size() - 1).getFieldName();
                
                String key = String.format("validate.user.%s.invalidType", field);
                String msg = messageSource.getMessage(key, null, locale);
                
                if (msg.equals(key)) {
                    key = String.format("validate.%s.invalidType", field);
                    msg = messageSource.getMessage(key, null, locale);
                }
                
                if (msg.equals(key)) {
                    msg = locale.getLanguage().equals("vi") 
                            ? field + " không đúng định dạng." 
                            : field + " is invalid format.";
                }
                
                errors.put(field, msg);
            }
        } else {
            String errorMsg = messageSource.getMessage("response.error.validateFailed", null, locale);
            errors.put("error", errorMsg);
        }
        Response<?> response = new Response<>(
            400,
            messageSource.getMessage("response.error.validateFailed", null, locale),
            "Request-Model",
            errors,
            null
        );
        return ResponseEntity.status(response.statusCode()).body(response);
    }

    // Unauthorized Exception Handler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedExceptionHandle.class)
    ResponseEntity<Response<?>> unauthorizedExceptionHandle(UnauthorizedExceptionHandle e) {
        Locale locale = LocaleContextHolder.getLocale();

        Map<String, String> errors = new HashMap<>();
        errors.put("Error", e.getMessage());

        Response<?> response = new Response<>(
            401,
            messageSource.getMessage("response.error.unauthorizedError", null, locale),
            e.getModelName() != null ? e.getModelName() : "Request-Model",
            errors,
            null
        );
        return ResponseEntity.status(response.statusCode()).body(response);
    }

    // Forbidden Exception Handler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenExceptionHandle.class)
    ResponseEntity<Response<?>> forbiddenExceptionHandle(ForbiddenExceptionHandle e) {
        Locale locale = LocaleContextHolder.getLocale();

        Map<String, String> errors = new HashMap<>();
        errors.put("Error", e.getMessage());
        if(e.getRequiredRole() != null){
            errors.put("RequiredRole", e.getRequiredRole());
        }

        Response<?> response = new Response<>(
            403,
            messageSource.getMessage("response.error.forbiddenError", null, locale),
            e.getModelName() != null ? e.getModelName() : "Request-Model",
            errors,
            null
        );
        return ResponseEntity.status(response.statusCode()).body(response);
    }

    // Service Unavailable Exception Handler
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ServiceUnavailableExceptionHandle.class)
    ResponseEntity<Response<?>> serviceUnavailableExceptionHandle(ServiceUnavailableExceptionHandle e) {
        Locale locale = LocaleContextHolder.getLocale();

        Map<String, String> errors = new HashMap<>();
        errors.put("Error", e.getMessage());

        Response<?> response = new Response<>(
            503,
            messageSource.getMessage("response.error.serviceUnavailableError", null, locale),
            e.getServiceName() != null ? e.getServiceName() : "Service",
            errors,
            null
        );
        return ResponseEntity.status(response.statusCode()).body(response);
    }

    // Too Many Requests Exception Handler
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ExceptionHandler(TooManyRequestsExceptionHandle.class)
    ResponseEntity<Response<?>> tooManyRequestsExceptionHandler(TooManyRequestsExceptionHandle e) {
        Locale locale = LocaleContextHolder.getLocale();

        Map<String, String> error = new HashMap<>();
        error.put("Error", e.getMessage());
        if (e.getRetryAfter() != null) {
            error.put("RetryAfter", String.valueOf(e.getRetryAfter()));
        }
        
        Response<?> response = new Response<>(
                429,
                messageSource.getMessage("response.error.tooManyRequests", null, locale),
                e.getServiceName() != null ? e.getServiceName() : "RateLimit",
                error,
                null
        );
        
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.statusCode());
        if (e.getRetryAfter() != null) {
            responseBuilder.header("Retry-After", String.valueOf(e.getRetryAfter()));
        }
        
        return responseBuilder.body(response);
    }

    // Exception Handler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ResponseEntity<Response<?>> exceptionHandler(Exception e) {
        Locale locale = LocaleContextHolder.getLocale();

        Map<String, String> error = new HashMap<>();
        error.put("Error", e.getMessage());
        
        Response<?> response = new Response<>(
                500,
                messageSource.getMessage("response.error.internalServerError", null, locale),
                "Exception",
                error,
                null
        );
        return ResponseEntity.status(response.statusCode()).body(response);
    }

}
