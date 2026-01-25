package com.common.models.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;   

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginModel {
    private String username;
    private String password;
}
