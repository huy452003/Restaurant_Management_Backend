package com.common.models;

import java.time.LocalDate;

import com.common.enums.Gender;
import com.common.enums.UserRole;
import com.common.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel extends BaseModel{
    private String username;
    private String fullname;
    private String email;
    private String phone;
    private Gender gender;
    private LocalDate birth;
    private Integer age;
    private String address;
    private UserRole role;
    private UserStatus userStatus;
}
