package com.common.entities;

import jakarta.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

import com.common.enums.ReservationStatus;

@Entity
@Table(name = "reservations")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor 
public class ReservationEntity extends BaseEntity {
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_phone")
    private String customerPhone;
    @Column(name = "customer_email")
    private String customerEmail;
    @Column(name = "table_id", insertable = false, updatable = false)
    private Integer tableId;
    @Column(name = "reservation_date")
    private LocalDate reservationDate;
    @Column(name = "reservation_time")
    private LocalTime reservationTime;
    @Column(name = "number_of_guests")
    private Integer numberOfGuests;
    @Column(name = "reservation_status")
    private ReservationStatus reservationStatus;
    @Column(name = "special_request")
    private String specialRequest;

    // version
    @Version
    @Column(name = "version")
    private Long version;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private TableEntity table;
}
