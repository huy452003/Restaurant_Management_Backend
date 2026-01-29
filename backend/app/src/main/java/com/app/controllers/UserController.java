package com.app.controllers;

import java.util.List;
import java.util.Locale;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.services.UserService;
import com.logging.models.LogContext;
import com.logging.services.LoggingService;

import jakarta.validation.Valid;

import com.common.models.UserModel;
import com.common.models.security.RegisterModel;
import com.common.models.Response;
import com.common.models.wrapperModel.UpdateUserNormalRequest;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private LoggingService log;

    private LogContext getLogContext(String methodName, List<Integer> userIds) {
        return LogContext.builder()
            .module("app")
            .className(this.getClass().getSimpleName())
            .methodName(methodName)
            .userIds(userIds)
            .build();
    }

    @GetMapping()
    public ResponseEntity<Response<List<UserModel>>> getAll(
        @RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage
    ) {
        Locale locale = Locale.forLanguageTag(acceptLanguage);
        LogContext logContext = getLogContext("getAll", Collections.emptyList());
        log.logInfo("is running, preparing to call service ...!", logContext);

        List<UserModel> users = userService.getAll();
        Response<List<UserModel>> response = new Response<>(
            200,
            messageSource.getMessage("response.message.getSuccess", null, locale),
            "userModel",
            null,
            users
        );
        log.logInfo("completed, returning response ...!", logContext);
        return ResponseEntity.status(response.statusCode()).body(response);
    }

    @PostMapping()
    public ResponseEntity<Response<List<UserModel>>> creates(
        @RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage,
        @RequestBody @Valid List<RegisterModel> registers
    ) {
        Locale locale = Locale.forLanguageTag(acceptLanguage);
        LogContext logContext = getLogContext("creates", Collections.emptyList());
        log.logInfo("is running, preparing to call service ...!", logContext);

        List<UserModel> savedUsers = userService.creates(registers);
        Response<List<UserModel>> response = new Response<>(
            201,
            messageSource.getMessage("response.message.createSuccess", null, locale),
            "userModel",
            null,
            savedUsers
        );
        log.logInfo("completed, returning response ...!", logContext);
        return ResponseEntity.status(response.statusCode()).body(response);
    }

    @PatchMapping()
    public ResponseEntity<Response<List<UserModel>>> updatesNormal(
        @RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage,
        @RequestBody @Valid UpdateUserNormalRequest request
    ) {
        Locale locale = Locale.forLanguageTag(acceptLanguage);
        LogContext logContext = getLogContext("updatesNormal", request.getUserIds());
        log.logInfo("is running, preparing to call service ...!", logContext);

        List<UserModel> updatedUsers = userService.updatesNormal(request.getUpdates(), request.getUserIds());
        Response<List<UserModel>> response = new Response<>(
            200,
            messageSource.getMessage("response.message.updateSuccess", null, locale),
            "userModel",
            null,
            updatedUsers
        );
        log.logInfo("completed, returning response ...!", logContext);
        return ResponseEntity.status(response.statusCode()).body(response);
    }

    @PutMapping("/{userId}/verify")
    public ResponseEntity<Response<UserModel>> verifyAndActivate(
        @RequestHeader(value = "Accept-Language", defaultValue = "en") String acceptLanguage,
        @PathVariable Integer userId,
        @RequestParam(required = false) String code
    ) {
        Locale locale = Locale.forLanguageTag(acceptLanguage);
        LogContext logContext = getLogContext("verifyAndActivate", Collections.singletonList(userId));
        log.logInfo("is running, preparing to call service ...!", logContext);

        // TODO: Nếu chưa có verification code system, có thể bỏ qua code parameter tạm thời
        String verificationCode = code != null ? code : "default"; // Tạm thời
        
        UserModel verifiedUser = userService.verifyAndActivate(userId, verificationCode);
        Response<UserModel> response = new Response<>(
            200,
            messageSource.getMessage("response.message.verifySuccess", new Object[]{"userModel"}, locale),
            "userModel",
            null,
            verifiedUser
        );
        log.logInfo("completed, returning response ...!", logContext);
        return ResponseEntity.status(response.statusCode()).body(response);
    }

}
