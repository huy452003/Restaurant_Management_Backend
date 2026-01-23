package com.common.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.common.enums.ShiftStatus;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "shifts")
@Data
@NoArgsConstructor
@AllArgsConstructor 
public class ShiftEntity extends BaseEntity {
    @Column(name = "employee_id")
    private Integer employeeId; // UserEntity
    @Column(name = "shift_date")
    private LocalDate shiftDate;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @Column(name = "status")
    private ShiftStatus status;
    @Column(name = "notes")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private UserEntity employee;
}
