package com.app.services.imp;

import com.app.services.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.models.UserModel;
import com.common.models.security.RegisterModel;
import com.common.repositories.UserRepository;
import com.common.enums.UserStatus;
import com.common.entities.UserEntity;
import com.common.utils.AgeUtils;
import com.common.models.UpdateUserNormalModel;

import com.logging.models.LogContext;
import com.logging.services.LoggingService;
import com.handle_exceptions.NotFoundExceptionHandle;
import com.handle_exceptions.ConflictExceptionHandle;
import com.handle_exceptions.ValidationExceptionHandle;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoggingService log;
    @Autowired
    private ModelMapper modelMapper;

    private LogContext getLogContext(String methodName, List<Integer> userIds){
        return LogContext.builder()
            .module("app")
            .className(this.getClass().getSimpleName())
            .methodName(methodName)
            .userIds(userIds)
            .build();
    }

    // get all users
    @Override
    public List<UserModel> getAll() {
        LogContext logContext = getLogContext("getAll", Collections.emptyList());
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

    // create users
    @Override
    public List<UserModel> creates(List<RegisterModel> registers) {
        LogContext logContext = getLogContext("creates", Collections.emptyList());
        log.logInfo("Creating " + registers.size() + " users ...!", logContext);

        // Kiểm tra trùng lặp trước khi lưu (normalize input để so sánh với data đã normalize trong DB)
        List<Object> conflicts = new ArrayList<>();
        for (RegisterModel register : registers) {
            // Normalize input
            register.setUsername(register.getUsername().toLowerCase().trim());
            register.setEmail(register.getEmail().toLowerCase().trim());
            register.setPhone(register.getPhone().trim());
            
            if (userRepository.existsByUsername(register.getUsername())) {
                Map<String, Object> conflict = new HashMap<>();
                conflict.put("field", "username");
                conflict.put("value", register.getUsername());
                conflict.put("message", "Username already exists");
                conflicts.add(conflict);
            }
            if (userRepository.existsByEmail(register.getEmail())) {
                Map<String, Object> conflict = new HashMap<>();
                conflict.put("field", "email");
                conflict.put("value", register.getEmail());
                conflict.put("message", "Email already exists");
                conflicts.add(conflict);
            }
            if (userRepository.existsByPhone(register.getPhone())) {
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

    // update user normal
    @Override
    public List<UserModel> updatesNormal(List<UpdateUserNormalModel> updates, List<Integer> userIds) {
        LogContext logContext = getLogContext("updatesNormal", userIds);
        log.logInfo("Updating " + updates.size() + " users ...!", logContext);

        // Validate size match
        if (updates.size() != userIds.size()) {
            ValidationExceptionHandle e = new ValidationExceptionHandle(
                "Number of updates must match number of user IDs",
                new ArrayList<>(),
                "userModel"
            );
            log.logError("Size mismatch between updates and userIds", logContext, e);
            throw e;
        }

        List<UserEntity> foundUsers = userIds.stream().map(id -> 
            userRepository.findById(id).orElseThrow(() -> {
                NotFoundExceptionHandle e = new NotFoundExceptionHandle(
                    "User not found with id: " + id,
                    new ArrayList<>(),
                    "userModel"
                );
                log.logError("User not found", logContext, e);
                return e;
            })
        ).collect(Collectors.toList());

        // kiểm tra trùng lặp trước khi cập nhật
        List<Object> conflicts = new ArrayList<>();
        for (int i = 0; i < updates.size(); i++) {
            UpdateUserNormalModel update = updates.get(i);
            UserEntity currentUser = foundUsers.get(i);
            
            // Normalize email và phone (không cần check null vì đã có @NotBlank validation)
            update.setEmail(update.getEmail().toLowerCase().trim());
            update.setPhone(update.getPhone().trim());
            
            // Check email: CHỈ check duplicate khi email THAY ĐỔI (khác với email hiện tại)
            if (!update.getEmail().equals(currentUser.getEmail())) {
                if (userRepository.existsByEmail(update.getEmail())) {
                    Map<String, Object> conflict = new HashMap<>();
                    conflict.put("field", "email");
                    conflict.put("value", update.getEmail());
                    conflict.put("message", "Email already exists for another user");
                    conflicts.add(conflict);
                }
            }
            
            // Check phone: CHỈ check duplicate khi phone THAY ĐỔI (khác với phone hiện tại)
            if (!update.getPhone().equals(currentUser.getPhone())) {
                if (userRepository.existsByPhone(update.getPhone())) {
                    Map<String, Object> conflict = new HashMap<>();
                    conflict.put("field", "phone");
                    conflict.put("value", update.getPhone());
                    conflict.put("message", "Phone already exists for another user");
                    conflicts.add(conflict);
                }
            }
        }

        if(!conflicts.isEmpty()){
            ConflictExceptionHandle e = new ConflictExceptionHandle(
                "Duplicate unique fields detected",
                conflicts,
                "userModel"
            );
            log.logError("Duplicate unique fields detected", logContext, e);
            throw e;
        }

        // Map các field từ update model vào UserEntity đã tồn tại (giữ nguyên các field khác)
        // Chỉ update những user có thay đổi thực sự
        List<UserEntity> usersToUpdate = new ArrayList<>();
        Iterator<UpdateUserNormalModel> updateIterator = updates.iterator();
        Iterator<UserEntity> userIterator = foundUsers.iterator();
        
        while (updateIterator.hasNext() && userIterator.hasNext()) {
            UpdateUserNormalModel update = updateIterator.next();
            UserEntity currentUser = userIterator.next();
            
            // Check xem có thay đổi gì không
            boolean hasChanges = !update.getFullname().equals(currentUser.getFullname()) ||
                                 !update.getEmail().equals(currentUser.getEmail()) ||
                                 !update.getPhone().equals(currentUser.getPhone()) ||
                                 !update.getGender().equals(currentUser.getGender()) ||
                                 !update.getBirth().equals(currentUser.getBirth()) ||
                                 !update.getAddress().equals(currentUser.getAddress());
            
            if (hasChanges) {
                // Map các field từ update model vào UserEntity
                modelMapper.map(update, currentUser);
                usersToUpdate.add(currentUser);
            }
        }

        // Chỉ save những user có thay đổi
        if (!usersToUpdate.isEmpty()) {
            userRepository.saveAll(usersToUpdate);
            log.logInfo("completed, updated " + usersToUpdate.size() + " users", logContext);
        } else {
            log.logInfo("completed, no changes detected, skipped update", logContext);
        }
        
        // Return tất cả users (bao gồm cả những user không có thay đổi)
        return foundUsers.stream().map(userEntity -> {
            UserModel userModel = modelMapper.map(userEntity, UserModel.class);
            if (userEntity.getBirth() != null) {
                userModel.setAge(AgeUtils.calculateAge(userEntity.getBirth()));
            }
            return userModel;
        }).collect(Collectors.toList());
    }

    // verify and activate user
    @Override
    public UserModel verifyAndActivate(Integer userId, String verificationCode) {
        LogContext logContext = getLogContext("verifyAndActivate", Collections.singletonList(userId));
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

    

}
