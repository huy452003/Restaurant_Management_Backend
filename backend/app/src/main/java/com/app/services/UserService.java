package com.app.services;

import java.util.List;

import com.common.models.UserModel;
import com.common.models.security.RegisterModel;
import com.common.models.UpdateUserNormalModel;
import com.common.models.UpdateUserForAdminModel;

public interface UserService {
    List<UserModel> getAll();
    List<UserModel> creates(List<RegisterModel> registers);
    List<UserModel> updatesNormal(List<UpdateUserNormalModel> updates, List<Integer> userIds);
    // List<UserModel> updatesForAdmin(List<UpdateUserForAdminModel> updates, List<Integer> userIds);
    UserModel verifyAndActivate(Integer userId, String verificationCode);
}
