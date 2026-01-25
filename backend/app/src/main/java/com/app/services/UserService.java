package com.app.services;

import java.util.List;

import com.common.models.UserModel;
import com.common.models.security.RegisterModel;

public interface UserService {
    List<UserModel> getAll();
    List<UserModel> creates(List<RegisterModel> registers);
    UserModel verifyAndActivate(Integer userId, String verificationCode);
    // List<UserModel> updates(UserModel userModel);
}
