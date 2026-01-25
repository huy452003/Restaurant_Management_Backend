package com.app.services.imp;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.services.UserService;
import com.common.models.UserModel;
import com.common.models.security.RegisterModel;
import com.common.repositories.UserRepository;
import com.common.enums.UserStatus;
import com.handle_exceptions.NotFoundExceptionHandle;
import com.handle_exceptions.ConflictExceptionHandle;
import com.logging.models.LogContext;
import com.logging.services.LoggingService;
import com.common.entities.UserEntity;
import com.common.utils.AgeUtils;


@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoggingService log;
    @Autowired
    private ModelMapper modelMapper;

    private LogContext getLogContext(String methodName, Integer userId){
        return LogContext.builder()
            .module("app")
            .className(this.getClass().getSimpleName())
            .methodName(methodName)
            .userId(userId)
            .build();
    }

    @Override
    public List<UserModel> getAll() {
        LogContext logContext = getLogContext("getAll", null);
        log.logInfo("Getting all users ...!", logContext);

        List<UserEntity> userEntities = userRepository.findAll();
        if(userEntities == null || userEntities.isEmpty()){
            NotFoundExceptionHandle e = new NotFoundExceptionHandle("No users found", new ArrayList<>(), "userModel");
            log.logError("threw an exception", logContext, e);
            throw e;
        }
        log.logInfo("completed, found " + userEntities.size() + " users", logContext);
        return userEntities.stream().map(
            userEntity -> {
            UserModel userModel = modelMapper.map(userEntity, UserModel.class);
                if(userEntity.getBirth() != null){
                    userModel.setAge(AgeUtils.calculateAge(userEntity.getBirth()));
                }
                return userModel;
            }
        ).collect(Collectors.toList());
    }

    @Override
    public List<UserModel> creates(List<RegisterModel> registers) {
        LogContext logContext = getLogContext("creates", null);
        log.logInfo("Creating " + registers.size() + " users ...!", logContext);

        // Kiểm tra trùng lặp trước khi lưu
        List<Object> conflicts = new ArrayList<>();
        for (RegisterModel register : registers) {
            if (register.getUsername() != null && userRepository.existsByUsername(register.getUsername())) {
                Map<String, Object> conflict = new HashMap<>();
                conflict.put("field", "username");
                conflict.put("value", register.getUsername());
                conflict.put("message", "Username already exists");
                conflicts.add(conflict);
            }
            if (register.getEmail() != null && userRepository.existsByEmail(register.getEmail())) {
                Map<String, Object> conflict = new HashMap<>();
                conflict.put("field", "email");
                conflict.put("value", register.getEmail());
                conflict.put("message", "Email already exists");
                conflicts.add(conflict);
            }
            if (register.getPhone() != null && userRepository.existsByPhone(register.getPhone())) {
                Map<String, Object> conflict = new HashMap<>();
                conflict.put("field", "phone");
                conflict.put("value", register.getPhone());
                conflict.put("message", "Phone already exists");
                conflicts.add(conflict);
            }
        }
        
        if (!conflicts.isEmpty()) {
            ConflictExceptionHandle e = new ConflictExceptionHandle(
                "Duplicate unique fields detected",
                conflicts,
                "userModel"
            );
            log.logError("Duplicate unique fields detected", logContext, e);
            throw e;
        }

        List<UserEntity> userEntities = registers.stream().map(register -> {
            UserEntity userEntity = modelMapper.map(register, UserEntity.class);
            if (userEntity.getUserStatus() == null) {
                userEntity.setUserStatus(UserStatus.PENDING);
            }
            return userEntity;
        }).collect(Collectors.toList());
    
        userRepository.saveAll(userEntities);
        log.logInfo("completed, created " + userEntities.size() + " users with PENDING status", logContext);
        
        // TODO: Gửi email/OTP xác thực ở đây
        
        return userEntities.stream().map(userEntity -> {
            UserModel userModel = modelMapper.map(userEntity, UserModel.class);
            if (userEntity.getBirth() != null) {
                userModel.setAge(AgeUtils.calculateAge(userEntity.getBirth()));
            }
            return userModel;
        }).collect(Collectors.toList());
    }

    @Override
    public UserModel verifyAndActivate(Integer userId, String verificationCode) {
        LogContext logContext = getLogContext("verifyAndActivate", userId);
        log.logInfo("Verifying user with code ...!", logContext);

        UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(() -> {
                NotFoundExceptionHandle e = new NotFoundExceptionHandle(
                    "User not found with id: " + userId,
                    new ArrayList<>(),
                    "userModel"
                );
                log.logError("User not found", logContext, e);
                return e;
            });

        // Kiểm tra status phải là PENDING
        if (userEntity.getUserStatus() != UserStatus.PENDING) {
            NotFoundExceptionHandle e = new NotFoundExceptionHandle(
                "User is not in PENDING status. Current status: " + userEntity.getUserStatus(),
                new ArrayList<>(),
                "userModel"
            );
            log.logError("User status is not PENDING", logContext, e);
            throw e;
        }

        // TODO: Verify code (OTP, email token, etc.)
        // if (!isValidVerificationCode(userId, verificationCode)) {
        //     throw new ValidationExceptionHandle("Invalid verification code", ...);
        // }

        // Activate user sau khi verify thành công
        userEntity.setUserStatus(UserStatus.ACTIVE);
        userRepository.save(userEntity);
        
        log.logInfo("User verified and activated successfully", logContext);
        
        UserModel userModel = modelMapper.map(userEntity, UserModel.class);
        if (userEntity.getBirth() != null) {
            userModel.setAge(AgeUtils.calculateAge(userEntity.getBirth()));
        }
        return userModel;
    }

    // @Override
    // public List<UserModel> updates(UserModel userModel) {
    //     return userRepository.save(userModel);
    // }

}
