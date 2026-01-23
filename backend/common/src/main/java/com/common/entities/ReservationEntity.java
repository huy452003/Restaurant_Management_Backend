package com.common.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

import com.common.enums.ReservationStatus;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor 
public class ReservationEntity extends BaseEntity {
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_phone")
    private String customerPhone;
    @Column(name = "customer_email")
    private String customerEmail;
    @Column(name = "table_id")
    private Integer tableId;
    @Column(name = "reservation_date")
    private LocalDate reservationDate;
    @Column(name = "reservation_time")
    private LocalTime reservationTime;
    @Column(name = "number_of_guests")
    private Integer numberOfGuests;
    @Column(name = "status")
    private ReservationStatus status;
    @Column(name = "special_request")
    private String specialRequest;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private TableEntity table;
}
