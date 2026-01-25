package com.common.models.security;

import com.common.enums.Gender;
import com.common.enums.UserRole;
import com.common.models.BaseModel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterModel extends BaseModel{
    @NotBlank(message = "validate.user.username.required")
    @Size(min = 3, max = 50, message = "validate.user.username.size")
    private String username;
    
    @NotBlank(message = "validate.user.password.required")
    @Size(min = 6, max = 100, message = "validate.user.password.size")
    private String password;
    
    @NotBlank(message = "validate.user.fullname.required")
    @Size(max = 100, message = "validate.user.fullname.size")
    private String fullname;
    
    @NotBlank(message = "validate.user.email.required")
    @Email(message = "validate.user.email.invalidFormat")
    private String email;
    
    @NotBlank(message = "validate.user.phone.required")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "validate.user.phone.invalidFormat")
    private String phone;
    
    @NotNull(message = "validate.user.gender.required")
    private Gender gender;
    
    private LocalDate birth;
    
    private String address;
    
    private UserRole role;
}
