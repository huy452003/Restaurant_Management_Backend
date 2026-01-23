package com.common.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.OneToMany;
import com.common.enums.UserRole;
import com.common.enums.UserStatus;
import com.common.enums.Gender;
import jakarta.persistence.FetchType;
import java.util.List;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "phone", unique = true)
    private String phone;
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "birth")
    private LocalDate birth;
    @Column(name = "address")
    private String address;
    @Column(name = "role")
    private UserRole role;
    @Column(name = "status")
    private UserStatus status;

    @OneToMany(mappedBy = "user_orders", fetch = FetchType.LAZY)
    private List<OrderEntity> orders;
    @OneToMany(mappedBy = "user_payments", fetch = FetchType.LAZY)
    private List<PaymentEntity> payments;
    @OneToMany(mappedBy = "user_shifts", fetch = FetchType.LAZY)
    private List<ShiftEntity> shifts;
}
