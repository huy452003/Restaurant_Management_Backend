package com.common.models;

import java.time.LocalDate;
import java.time.LocalTime;

import com.common.enums.ReservationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationModel extends BaseModel{
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private Integer tableId;
    private LocalDate reservationDate;
    private LocalTime reservationTime;
    private Integer numberOfGuests;
    private ReservationStatus reservationStatus;
    private String specialRequest;
}
